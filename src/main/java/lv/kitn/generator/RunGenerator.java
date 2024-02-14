package lv.kitn.generator;

import static com.google.common.collect.ImmutableMap.toImmutableMap;
import static com.google.common.collect.ImmutableSet.toImmutableSet;
import static com.google.common.collect.ImmutableSetMultimap.toImmutableSetMultimap;
import static lv.kitn.mapadjacency.MapAdjacencyService.getGroups;
import static lv.kitn.state.Terrain.LAKES;
import static lv.kitn.state.Terrain.OCEAN;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import lv.kitn.mapadjacency.MapAdjacencyService;
import lv.kitn.state.Building;
import lv.kitn.state.BuildingGroup;
import lv.kitn.state.BuildingLoader;
import lv.kitn.state.Color;
import lv.kitn.state.Country;
import lv.kitn.state.Culture;
import lv.kitn.state.DiscoverableResource;
import lv.kitn.state.Population;
import lv.kitn.state.Province;
import lv.kitn.state.ProvinceLoader;
import lv.kitn.state.RegionState;
import lv.kitn.state.State;
import lv.kitn.state.StateWriter;
import lv.kitn.state.StrategicRegion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
class RunGenerator implements CommandLineRunner {
  private static final Logger LOG = LoggerFactory.getLogger(RunGenerator.class);

  private final FileProperties properties;

  private final MapAdjacencyService mapAdjacencyService;

  private final ProvinceLoader provinceLoader;

  private final Random random;

  RunGenerator(
      FileProperties properties,
      MapAdjacencyService mapAdjacencyService,
      ProvinceLoader provinceLoader,
      Random random) {
    this.properties = properties;
    this.mapAdjacencyService = mapAdjacencyService;
    this.provinceLoader = provinceLoader;
    this.random = random;
  }

  @Override
  public void run(String... args) throws Exception {
    var input = properties.input();
    var provinces =
        provinceLoader.loadProvinces(input.gameInstallationPath() + input.provinceTerrains());

    var landProvinces =
        provinces.stream()
            .filter(province -> province.terrain() != OCEAN && province.terrain() != LAKES)
            .map(Province::id)
            .collect(toImmutableSet());

    var adjacencyMatrix =
        mapAdjacencyService.findAdjacencyMatrix(
            input.gameInstallationPath() + input.provinceImage());

    var landAdjacencyMatrix =
        adjacencyMatrix.entries().stream()
            .filter(
                entry ->
                    landProvinces.contains(entry.getKey())
                        && landProvinces.contains(entry.getValue()))
            .collect(toImmutableSetMultimap(Map.Entry::getKey, Map.Entry::getValue));

    var additionalAdjacency =
        mapAdjacencyService.loadAdditionalAdjacencyMatrix(
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

    Country country = new Country("AAA");

    var states = generateStates(groupedProvinces, buildings);

    var stateAdjacency = calculateStateAdjacency(states, fullAdjacency);

    var regionStates = generateRegionStates(states, country);
    var strategicRegions = generateStrategicRegions(states, stateAdjacency, random);

    var seaStates = ImmutableSet.<State>of();
    var seaStrategicRegions = ImmutableSet.<StrategicRegion>of();

    var output = properties.output();

    StateWriter.writeHistoryStates(regionStates, output.modPath() + output.states());
    StateWriter.writeHistoryPops(regionStates, output.modPath() + output.pops());
    StateWriter.writeHistoryBuildings(regionStates, output.modPath() + output.buildings());
    StateWriter.writeStrategicRegions(
        strategicRegions, output.modPath() + output.strategicRegions());
    StateWriter.writeStateRegions(states, output.modPath() + output.stateRegions());

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
              ImmutableMap.of(new BuildingGroup("bg_coal_mining "), 13),
              ImmutableSet.of(
                  new DiscoverableResource(
                      new BuildingGroup("bg_rubber "), Optional.empty(), 23, Optional.empty())),
              Optional.empty()));
      i++;
    }
    return result.build();
  }

  private static ImmutableSet<RegionState> generateRegionStates(
      ImmutableSet<State> states, Country country) {
    LOG.debug("Generating region states");
    var result = ImmutableSet.<RegionState>builder();
    for (var state : states) {
      result.add(
          new RegionState(
              state,
              country,
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
}
