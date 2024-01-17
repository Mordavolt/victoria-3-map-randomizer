package generator;

import building.Building;
import building.BuildingGroup;
import building.BuildingLoader;
import country.Country;
import java.util.Collection;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import province.Province;
import province.ProvinceLoader;
import state.RegionState;
import state.State;

@Component
class RunGenerator implements CommandLineRunner {

  private final FileProperties properties;

  RunGenerator(FileProperties properties) {
    this.properties = properties;
  }

  @Override
  public void run(String... args) throws Exception {
    List<Province> provinces =
        ProvinceLoader.loadProvinces(
            properties.gameInstallationPath() + properties.provinceTerrains());
    List<BuildingGroup> buildingGroups =
        BuildingLoader.loadBuildingGroups(
            properties.gameInstallationPath() + properties.buildingGroups());
    List<Building> buildings =
        properties.buildings().stream()
            .map(path -> properties.gameInstallationPath() + path)
            .map(BuildingLoader::loadBuildings)
            .flatMap(Collection::stream)
            .toList();

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
}
