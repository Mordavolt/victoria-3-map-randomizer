package lv.kitn.generator;

import java.util.List;

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
      List<String> emptyFilesToCreate,
      String states,
      String pops,
      String buildings,
      String countries,
      String strategicRegions,
      String stateRegions) {}
}
