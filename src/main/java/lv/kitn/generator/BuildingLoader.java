package lv.kitn.generator;

import static com.google.common.collect.ImmutableList.toImmutableList;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import lv.kitn.pdxfile.PdxFileReader;
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
                  buildingGroup,
                  productionMethodGroups.stream()
                      .map(ProductionMethodGroup::new)
                      .collect(toImmutableList()));
            })
        .toList();
  }

  public static List<BuildingGroup> loadBuildingGroups(String filePath) {
    try {
      return getBuildingGroups(CharStreams.fromFileName(filePath));
    } catch (Exception e) {
      throw new RuntimeException("Could not read building groups from " + filePath, e);
    }
  }

  static List<BuildingGroup> getBuildingGroups(CharStream charStream) {
    return PdxFileReader.parseFileToDataMap(charStream).entrySet().stream()
        .map(
            entry ->
                new BuildingGroup(
                    entry.getKey(),
                    Optional.ofNullable(
                        (String) ((Map<String, Object>) entry.getValue()).get("parent_group"))))
        .toList();
  }
}
