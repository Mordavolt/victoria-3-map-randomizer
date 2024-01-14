package generator;

import java.io.IOException;
import java.util.Properties;

record GeneratorProperties(
    String vanillaGamePath,
    String vanillaProvincesPath,
    String vanillaSubsistenceBuildingsFilePath,
    String vanillaBuildingGroupsFilePath) {
  private static final String GENERATOR_PROPERTIES_FILE_NAME = "generator.properties";

  static GeneratorProperties readProperties() {

    var properties = new Properties();
    try {
      var resourceAsStream =
          Thread.currentThread()
              .getContextClassLoader()
              .getResourceAsStream(GENERATOR_PROPERTIES_FILE_NAME);
      properties.load(resourceAsStream);
    } catch (IOException e) {
      throw new RuntimeException(
          "generator.properties file must exist in the same folder as this class", e);
    }

    var vanillaGamePath = properties.getProperty("game_installation_path");
    var vanillaProvincesPath =
        vanillaGamePath + "/" + properties.getProperty("vanilla_province_terrains_file_path");
    var vanillaSubsistenceBuildingsFilePath =
        vanillaGamePath + "/" + properties.getProperty("vanilla_subsistence_buildings_file_path");
    var vanillaBuildingGroupsFilePath =
        vanillaGamePath + "/" + properties.getProperty("vanilla_building_groups_file_path");
    return new GeneratorProperties(
        vanillaGamePath,
        vanillaProvincesPath,
        vanillaSubsistenceBuildingsFilePath,
        vanillaBuildingGroupsFilePath);
  }
}
