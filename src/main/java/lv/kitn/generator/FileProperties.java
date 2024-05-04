package lv.kitn.generator;

import java.util.List;

record FileProperties(Input input, Output output) {
  record Input(
      String gameInstallationPath,
      String provinceImage,
      String provinceTerrains,
      String adjacencies,
      List<String> buildings,
      String buildingGroups,
      List<String> cultures) {}

  record Output(
      String modPath,
      String metadata,
      List<String> emptyFilesToCreate,
      String states,
      String pops,
      String buildings,
      String countries,
      String countryLocalization,
      String countryDefinitions,
      String strategicRegions,
      String stateRegions,
      String stateLocalization) {}
}
