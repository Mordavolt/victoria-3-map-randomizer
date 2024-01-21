package lv.kitn.generator;

import static com.google.common.collect.ImmutableSet.toImmutableSet;
import static lv.kitn.province.Terrain.LAKES;
import static lv.kitn.province.Terrain.OCEAN;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import lv.kitn.mapadjacency.MapAdjacencyExtractor;
import lv.kitn.province.Province;
import lv.kitn.province.ProvinceLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
class RunGenerator implements CommandLineRunner {

  private final FileProperties properties;

  private final MapAdjacencyExtractor mapAdjacencyExtractor;

  private final ProvinceLoader provinceLoader;

  private final Random random;

  RunGenerator(
      FileProperties properties,
      MapAdjacencyExtractor mapAdjacencyExtractor,
      ProvinceLoader provinceLoader,
      Random random) {
    this.properties = properties;
    this.mapAdjacencyExtractor = mapAdjacencyExtractor;
    this.provinceLoader = provinceLoader;
    this.random = random;
  }

  @Override
  public void run(String... args) throws Exception {
    var provinces =
        provinceLoader.loadProvinces(
            properties.gameInstallationPath() + properties.provinceTerrains());

    var adjacencyMatrix =
        mapAdjacencyExtractor.findAdjacencyMatrix(
            properties.gameInstallationPath() + properties.provinceImage());

    var sets = groupAdjacentStates(provinces, adjacencyMatrix, 5, 10);

    //    List<BuildingGroup> buildingGroups =
    //        BuildingLoader.loadBuildingGroups(
    //            properties.gameInstallationPath() + properties.buildingGroups());
    //    List<Building> buildings =
    //        properties.buildings().stream()
    //            .map(path -> properties.gameInstallationPath() + path)
    //            .map(BuildingLoader::loadBuildings)
    //            .flatMap(Collection::stream)
    //            .toList();

  }

  private static Set<Set<String>> groupAdjacentStates(
      Set<Province> provinces,
      ImmutableSetMultimap<String, String> adjacencyMatrix,
      Integer minGroupSize,
      Integer maxGroupSize) {

    var landProvinces =
        provinces.stream()
            .filter(province -> province.terrain() != OCEAN && province.terrain() != LAKES)
            .map(Province::id)
            .collect(toImmutableSet());

    var groupedProvinces = ImmutableSet.<Set<String>>builder();
    var visited = new HashSet<>();

    for (String province : landProvinces) {
      if (!visited.contains(province)) {
        var group = new HashSet<String>();
        var stack = new ArrayDeque<String>();
        stack.push(province);

        while (!stack.isEmpty() && group.size() < maxGroupSize) {
          var current = stack.pop();
          if (!visited.contains(current)) {
            visited.add(current);
            group.add(current);

            for (var adjacent : adjacencyMatrix.get(current)) {
              if (!visited.contains(adjacent)) {
                stack.push(adjacent);
              }
            }
          }
        }

        // Check if the group size is within the limits
        if (group.size() >= minGroupSize) {
          groupedProvinces.add(ImmutableSet.copyOf(group));
        }
      }
    }

    return groupedProvinces.build();
  }
}
