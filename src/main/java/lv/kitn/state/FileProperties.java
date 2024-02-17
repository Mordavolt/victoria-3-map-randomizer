package lv.kitn.state;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("files")
record FileProperties(Input input, Output output) {
  record Input(
      String gameInstallationPath,
      String provinceImage,
      String provinceTerrains,
      String adjacencies,
      List<String> buildings,
      String buildingGroups) {}

  record Output(
      String modPath,
      String states,
      String pops,
      String buildings,
      String countries,
      String strategicRegions,
      String stateRegions) {}
}
