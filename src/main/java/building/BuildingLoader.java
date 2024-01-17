package building;

import static java.util.stream.Collectors.toList;

import file_reader.PdxFileReader;
import java.util.List;
import java.util.Map;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;

public class BuildingLoader {
  public static List<Building> loadBuildings(String filePath) {
    try {
      return getBuildings(CharStreams.fromFileName(filePath));
    } catch (Exception e) {
      throw new RuntimeException("Could not read buildings from " + filePath, e);
    }
  }

  static List<Building> getBuildings(CharStream charStream) {
    return PdxFileReader.parseFileToDataMap(charStream).entrySet().stream()
        .map(
            entry -> {
              var id = entry.getKey();
              var buildingProperties = (Map<String, Object>) entry.getValue();
              var buildingGroup = (String) buildingProperties.get("building_group");
              var productionMethodGroups =
                  (List<String>) buildingProperties.get("production_method_groups");
              return new Building(
                  id,
                  new BuildingGroup(buildingGroup),
                  productionMethodGroups.stream()
                      .map(ProductionMethodGroup::new)
                      .collect(toList()));
            })
        .collect(toList());
  }

  public static List<BuildingGroup> loadBuildingGroups(String filePath) {
    try {
      return getBuildingGroups(CharStreams.fromFileName(filePath));
    } catch (Exception e) {
      throw new RuntimeException("Could not read building groups from " + filePath, e);
    }
  }

  static List<BuildingGroup> getBuildingGroups(CharStream charStream) {
    return PdxFileReader.parseFileToDataMap(charStream).keySet().stream()
        .map(BuildingGroup::new)
        .collect(toList());
  }
}
