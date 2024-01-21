package lv.kitn.generator;

import lv.kitn.mapadjacency.MapAdjacencyExtractor;
import lv.kitn.province.ProvinceLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
class RunGenerator implements CommandLineRunner {

  private final FileProperties properties;

  private final MapAdjacencyExtractor mapAdjacencyExtractor;

  private final ProvinceLoader provinceLoader;

  RunGenerator(
      FileProperties properties,
      MapAdjacencyExtractor mapAdjacencyExtractor,
      ProvinceLoader provinceLoader) {
    this.properties = properties;
    this.mapAdjacencyExtractor = mapAdjacencyExtractor;
    this.provinceLoader = provinceLoader;
  }

  @Override
  public void run(String... args) throws Exception {
    var provinces =
        provinceLoader.loadProvinces(
            properties.gameInstallationPath() + properties.provinceTerrains());

    var adjacencyMatrix =
        mapAdjacencyExtractor.findAdjacencyMatrix(
            properties.gameInstallationPath() + properties.provinceImage());

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
}
