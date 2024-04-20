package lv.kitn.generator;

import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;
import static com.google.common.collect.ImmutableMap.toImmutableMap;
import static com.google.common.collect.ImmutableSet.toImmutableSet;
import static com.google.common.collect.ImmutableSetMultimap.toImmutableSetMultimap;
import static java.nio.charset.StandardCharsets.UTF_8;
import static lv.kitn.generator.MapAdjacencyService.getGroups;
import static lv.kitn.generator.Politics.TRADITIONAL;
import static lv.kitn.generator.Terrain.LAKES;
import static lv.kitn.generator.Terrain.OCEAN;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Maps;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class RunGenerator {
  private static final Logger LOG = LoggerFactory.getLogger(RunGenerator.class);

  public static void main(String... args) {
    var random = new Random();
    var properties = getProperties();
    var input = properties.input();
    var provinces =
        ProvinceLoader.loadProvinces(input.gameInstallationPath() + input.provinceTerrains());

    var landProvinces =
        provinces.stream()
            .filter(province -> province.terrain() != OCEAN && province.terrain() != LAKES)
            .map(Province::id)
            .collect(toImmutableSet());

    var adjacencyMatrix =
        MapAdjacencyService.findAdjacencyMatrix(
            input.gameInstallationPath() + input.provinceImage());

    var landAdjacencyMatrix =
        adjacencyMatrix.entries().stream()
            .filter(
                entry ->
                    landProvinces.contains(entry.getKey())
                        && landProvinces.contains(entry.getValue()))
            .collect(toImmutableSetMultimap(Map.Entry::getKey, Map.Entry::getValue));

    var additionalAdjacency =
        MapAdjacencyService.loadAdditionalAdjacencyMatrix(
            input.gameInstallationPath() + input.adjacencies());

    var fullAdjacency =
        ImmutableSetMultimap.<String, String>builder()
            .putAll(landAdjacencyMatrix)
            .putAll(additionalAdjacency)
            .build();

    //
    //    var buildingGroups =
    //        BuildingLoader.loadBuildingGroups(input.gameInstallationPath() +
    // input.buildingGroups());
    var buildings =
        input.buildings().stream()
            .map(path -> input.gameInstallationPath() + path)
            .map(BuildingLoader::loadBuildings)
            .flatMap(Collection::stream)
            .toList();

    var groupedProvinces = getGroups(fullAdjacency, 100);

    var countries =
        ImmutableSet.of(
            new Country(
                "AAA",
                ImmutableSet.of(),
                ImmutableSet.of(),
                ImmutableSet.of(),
                TRADITIONAL,
                4,
                Optional.empty(),
                ImmutableMap.of(),
                2,
                ImmutableMap.of(),
                ImmutableSet.of(),
                ImmutableMap.of(),
                ImmutableMap.of(),
                ImmutableSet.of(),
                ImmutableMap.of()));

    var states = generateStates(groupedProvinces, buildings);

    var stateAdjacency = calculateStateAdjacency(states, fullAdjacency);

    var regionStates = generateRegionStates(states, countries);
    var strategicRegions = generateStrategicRegions(states, stateAdjacency, random);

    //    var seaStates = ImmutableSet.<State>of();
    //    var seaStrategicRegions = ImmutableSet.<StrategicRegion>of();

    var output = properties.output();

    writeMetadata(output.modPath() + output.metadata(), getMetadata());
    writeEmptyFilesForOverride(output.modPath(), output.emptyFilesToCreate());
    StateWriter.writeHistoryStates(regionStates, output.modPath() + output.states());
    StateWriter.writeHistoryPops(regionStates, output.modPath() + output.pops());
    StateWriter.writeHistoryBuildings(regionStates, output.modPath() + output.buildings());
    StateWriter.writeStrategicRegions(
        strategicRegions, output.modPath() + output.strategicRegions());
    StateWriter.writeStateRegions(states, output.modPath() + output.stateRegions());
    CountryWriter.writeHistoryCountries(countries, output.modPath() + output.countries());

    LOG.debug("Generation done");
  }

  private static ImmutableSetMultimap<String, String> calculateStateAdjacency(
      ImmutableSet<State> states, ImmutableSetMultimap<String, String> fullAdjacency) {
    LOG.debug("Calculating state adjacency");

    var provinceToState =
        states.stream()
            .flatMap(
                state ->
                    state.provinces().stream()
                        .map(province -> Map.entry(province, state.variableName())))
            .collect(toImmutableMap(Map.Entry::getKey, Map.Entry::getValue));
    return fullAdjacency.entries().stream()
        .map(e -> Map.entry(provinceToState.get(e.getKey()), provinceToState.get(e.getValue())))
        .filter(e -> !e.getKey().equals(e.getValue()))
        .collect(toImmutableSetMultimap(Map.Entry::getKey, Map.Entry::getValue));
  }

  private static ImmutableSet<State> generateStates(
      ImmutableSet<ImmutableSet<String>> groupedProvinces, List<Building> buildings) {
    LOG.debug("Generating states");
    var result = ImmutableSet.<State>builder();
    int i = 0;
    for (var provinces : groupedProvinces) {
      result.add(
          new State(
              "STATE_" + i,
              i,
              ImmutableList.of(new Culture("malay")),
              buildings.stream()
                  .filter(b -> b.buildingGroup().id().equals("bg_subsistence_agriculture"))
                  .findAny()
                  .orElseThrow(),
              provinces,
              ImmutableSet.of(),
              ImmutableSet.of(),
              ImmutableSet.of(),
              ImmutableMap.of(),
              20,
              ImmutableSet.of(new BuildingGroup("bg_rice_farms")),
              ImmutableMap.of(new BuildingGroup("bg_coal_mining"), 13),
              ImmutableSet.of(
                  new DiscoverableResource(
                      new BuildingGroup("bg_rubber"), Optional.empty(), 23, Optional.empty())),
              Optional.empty()));
      i++;
    }
    return result.build();
  }

  private static ImmutableSet<RegionState> generateRegionStates(
      ImmutableSet<State> states, ImmutableSet<Country> countries) {
    LOG.debug("Generating region states");
    var result = ImmutableSet.<RegionState>builder();
    for (var state : states) {
      result.add(
          new RegionState(
              state,
              countries.iterator().next(),
              state.provinces(),
              ImmutableSet.of(
                  new Population(new Culture("malay"), 100_00, Optional.empty(), Optional.empty())),
              ImmutableSet.of()));
    }
    return result.build();
  }

  private static ImmutableSet<StrategicRegion> generateStrategicRegions(
      ImmutableSet<State> states,
      ImmutableSetMultimap<String, String> stateAdjacency,
      Random random) {
    LOG.debug("Generating strategic regions");
    var stateMap = Maps.uniqueIndex(states, State::variableName);
    var stateGroups =
        getGroups(stateAdjacency, 10).stream()
            .map(stateNames -> stateNames.stream().map(stateMap::get).collect(toImmutableSet()))
            .collect(toImmutableSet());
    var result = ImmutableSet.<StrategicRegion>builder();
    var i = 0;
    for (ImmutableSet<State> group : stateGroups) {
      result.add(
          new StrategicRegion(
              "strategic_" + i++,
              "asian",
              group.iterator().next().provinces().iterator().next(),
              new Color(random.nextDouble(), random.nextDouble(), random.nextDouble()),
              group));
    }
    return result.build();
  }

  private static void writeEmptyFilesForOverride(String modPath, List<String> emptyFilesToCreate) {
    for (String relativePath : emptyFilesToCreate) {
      var filePath = modPath + relativePath;
      LOG.debug("Writing an empty file to {}", filePath);
      try {
        new File(filePath).getParentFile().mkdirs();
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, UTF_8));
        writer.write('\ufeff');
        writer.newLine();
        writer.close();
      } catch (Exception e) {
        throw new RuntimeException("Could not write an empty file to " + filePath, e);
      }
    }
  }

  private static void writeMetadata(String metadataPath, Metadata metadata) {
    LOG.debug("Writing metadata to {}", metadataPath);
    try {
      new File(metadataPath).getParentFile().mkdirs();
      BufferedWriter writer = new BufferedWriter(new FileWriter(metadataPath, UTF_8));

      ObjectMapper objectMapper = new ObjectMapper().enable(INDENT_OUTPUT);
      objectMapper.writeValue(writer, metadata);
    } catch (Exception e) {
      throw new RuntimeException("Could not write metadata to " + metadataPath, e);
    }
  }

  private static FileProperties getProperties() {
    var gamePath = "C:/Program Files (x86)/Steam/steamapps/common/Victoria 3/game";
    var modPath = "C:/Users/Admin/Documents/Paradox Interactive/Victoria 3/mod/random_world";
    return new FileProperties(
        new FileProperties.Input(
            gamePath,
            "/map_data/provinces.png",
            "/map_data/province_terrains.txt",
            "/map_data/adjacencies.csv",
            List.of(
                "/common/buildings/01_industry.txt",
                "/common/buildings/02_agro.txt",
                "/common/buildings/03_mines.txt",
                "/common/buildings/04_plantations.txt",
                "/common/buildings/05_military.txt",
                "/common/buildings/06_urban_center.txt",
                "/common/buildings/07_government.txt",
                "/common/buildings/08_monuments.txt",
                "/common/buildings/09_misc_resource.txt",
                "/common/buildings/10_canals.txt",
                "/common/buildings/11_private_infrastructure.txt",
                "/common/buildings/12_subsistence.txt",
                "/common/buildings/13_construction.txt"),
            "/common/building_groups/00_building_groups.txt"),
        new FileProperties.Output(
            modPath,
            "/.metadata/metadata.json",
            List.of(
                "/map_data/state_regions/00_west_europe.txt",
                "/map_data/state_regions/01_south_europe.txt",
                "/map_data/state_regions/02_east_europe.txt",
                "/map_data/state_regions/03_north_africa.txt",
                "/map_data/state_regions/04_subsaharan_africa.txt",
                "/map_data/state_regions/05_north_america.txt",
                "/map_data/state_regions/06_central_america.txt",
                "/map_data/state_regions/07_south_america.txt",
                "/map_data/state_regions/08_middle_east.txt",
                "/map_data/state_regions/09_central_asia.txt",
                "/map_data/state_regions/10_india.txt",
                "/map_data/state_regions/11_east_asia.txt",
                "/map_data/state_regions/12_indonesia.txt",
                "/map_data/state_regions/13_australasia.txt",
                "/map_data/state_regions/14_siberia.txt"),
            "/common/history/states/00_states.txt",
            "/common/history/pops/00_pops.txt",
            "/common/history/buildings/00_buildings.txt",
            "/common/history/countries/00_countries.txt",
            "/common/strategic_regions/00_strategic_regions.txt",
            "/map_data/state_regions/00_state_regions.txt"));
  }

  private static Metadata getMetadata() {
    return new Metadata(
        "Random World",
        "random_world",
        "0.0.1",
        "victoria3",
        List.of("random"),
        "1.6.*",
        "Random World",
        new Metadata.GameCustomData(true));
  }
}
