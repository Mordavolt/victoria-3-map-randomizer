package lv.kitn.generator;

import static com.google.common.collect.ImmutableSet.toImmutableSet;
import static lv.kitn.mapadjacency.MapAdjacencyService.getGroups;
import static lv.kitn.state.Terrain.LAKES;
import static lv.kitn.state.Terrain.OCEAN;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import lv.kitn.mapadjacency.MapAdjacencyService;
import lv.kitn.state.Building;
import lv.kitn.state.BuildingGroup;
import lv.kitn.state.BuildingLoader;
import lv.kitn.state.Country;
import lv.kitn.state.Culture;
import lv.kitn.state.Population;
import lv.kitn.state.Province;
import lv.kitn.state.ProvinceLoader;
import lv.kitn.state.RegionState;
import lv.kitn.state.State;
import lv.kitn.state.StateWriter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
class RunGenerator implements CommandLineRunner {

  private final FileProperties properties;

  private final MapAdjacencyService mapAdjacencyService;

  private final ProvinceLoader provinceLoader;

  RunGenerator(
      FileProperties properties,
      MapAdjacencyService mapAdjacencyService,
      ProvinceLoader provinceLoader,
      Random random) {
    this.properties = properties;
    this.mapAdjacencyService = mapAdjacencyService;
    this.provinceLoader = provinceLoader;
  }

  @Override
  public void run(String... args) throws Exception {
    var input = properties.input();
    var provinces =
        provinceLoader.loadProvinces(input.gameInstallationPath() + input.provinceTerrains());

    var adjacencyMatrix =
        mapAdjacencyService.findAdjacencyMatrix(
            input.gameInstallationPath() + input.provinceImage());

    var additionalAdjacency =
        mapAdjacencyService.loadAdditionalAdjacencyMatrix(
            input.gameInstallationPath() + input.adjacencies());

    var fullAdjacency =
        ImmutableSetMultimap.<String, String>builder()
            .putAll(adjacencyMatrix)
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

    var groupedProvinces = groupAdjacentProvinces(provinces, fullAdjacency, 100);

    Country country = new Country("AAA");

    var states = generateStates(groupedProvinces, buildings);
    var regionStates = generateRegionStates(states, country);

    var output = properties.output();

    StateWriter.writeHistoryStates(regionStates, output.modPath() + output.states());
    StateWriter.writeHistoryPops(regionStates, output.modPath() + output.pops());

    log.debug("Generation done");
  }

  private ImmutableSet<RegionState> generateRegionStates(
      ImmutableSet<State> states, Country country) {
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

  private static ImmutableSet<State> generateStates(
      ImmutableSet<ImmutableSet<String>> groupedProvinces, List<Building> buildings) {
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
              ImmutableMap.of(),
              20,
              ImmutableSet.of(new BuildingGroup("bg_rice_farms")),
              ImmutableMap.of(new BuildingGroup("bg_coal_mining "), 13),
              ImmutableMap.of(new BuildingGroup("bg_rubber "), 23),
              Optional.empty()));
      i++;
    }
    return result.build();
  }

  private static ImmutableSet<ImmutableSet<String>> groupAdjacentProvinces(
      Set<Province> provinces,
      ImmutableSetMultimap<String, String> adjacencyMatrix,
      Integer minGroupSize) {

    var landProvinces =
        provinces.stream()
            .filter(province -> province.terrain() != OCEAN && province.terrain() != LAKES)
            .map(Province::id)
            .collect(toImmutableSet());

    return getGroups(landProvinces, adjacencyMatrix, minGroupSize);
  }
}
