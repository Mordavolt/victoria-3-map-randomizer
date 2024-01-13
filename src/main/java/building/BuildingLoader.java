package building;

import static java.util.stream.Collectors.toList;

import file_reader.PdxFileReader;
import java.io.IOException;
import java.util.List;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;

public class BuildingLoader {
  public static List<SubsistenceBuilding> loadSubsistenceBuildings(String filePath) {
    try {
      return getSubsistenceBuildings(CharStreams.fromFileName(filePath));
    } catch (IOException e) {
      throw new RuntimeException("Could not read subsistence buildings from " + filePath, e);
    }
  }

  static List<SubsistenceBuilding> getSubsistenceBuildings(CharStream charStream) {
    return PdxFileReader.parseFileToDataMap(charStream).keySet().stream()
        .map(SubsistenceBuilding::new)
        .collect(toList());
  }

  public static List<BuildingGroup> loadBuildingGroups(String filePath) {
    try {
      return getBuildingGroups(CharStreams.fromFileName(filePath));
    } catch (IOException e) {
      throw new RuntimeException("Could not read building groups from " + filePath, e);
    }
  }

  static List<BuildingGroup> getBuildingGroups(CharStream charStream) {
    return PdxFileReader.parseFileToDataMap(charStream).keySet().stream()
            .map(BuildingGroup::new)
            .collect(toList());
  }
}
