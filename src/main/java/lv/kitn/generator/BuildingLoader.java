package lv.kitn.generator;

import static com.google.common.collect.ImmutableList.toImmutableList;

import java.util.List;
import java.util.Locale;
import java.util.Map;
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
              var buildingGroup =
                  BuildingGroup.valueOf(
                      ((String) buildingProperties.get("building_group")).toUpperCase(Locale.ROOT));
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
    return PdxFileReader.parseFileToDataMap(charStream).keySet().stream()
        .map(s -> s.toUpperCase(Locale.ROOT))
        .map(BuildingGroup::valueOf)
        .toList();
  }
}
