package generator;

import building.Building;
import building.BuildingGroup;
import building.BuildingLoader;
import country.Country;
import java.util.List;
import province.Province;
import province.ProvinceLoader;
import state.RegionState;
import state.State;

public class Generator {
  public static void main(String[] args) {
    var properties = loadProperties();

    List<Province> provinces = ProvinceLoader.loadProvinces(properties.vanillaProvincesPath());
    List<BuildingGroup> buildingGroups =
        BuildingLoader.loadBuildingGroups(properties.vanillaBuildingGroupsFilePath());
    List<Building> buildings =
        BuildingLoader.loadSubsistenceBuildings(properties.vanillaSubsistenceBuildingsFilePath());

    var states = createStates();
    var countries = createCountries();
    distributeStatesToCountries(states, countries);
  }

  private static List<RegionState> distributeStatesToCountries(
      List<State> states, List<Country> countries) {
    // XXX: Implement me
    return null;
  }

  private static List<State> createStates() {
    // XXX: Implement me
    return null;
  }

  private static List<Country> createCountries() {
    // XXX: Implement me
    return null;
  }

  private static GeneratorProperties loadProperties() {
    return GeneratorProperties.readProperties();
  }
}
