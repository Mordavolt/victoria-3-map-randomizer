import building.BuildingGroup;
import building.BuildingLoader;
import building.SubsistenceBuilding;
import province.Province;
import province.ProvinceLoader;
import java.util.List;

public class Generator {
  public static void main(String[] args) {
    var properties = loadProperties();

    List<Province> provinces = ProvinceLoader.loadProvinces(properties.vanillaProvincesPath());
    List<BuildingGroup> buildingGroups =
        BuildingLoader.loadBuildingGroups(properties.vanillaBuildingGroupsFilePath());
    List<SubsistenceBuilding> subsistenceBuildings =
        BuildingLoader.loadSubsistenceBuildings(properties.vanillaSubsistenceBuildingsFilePath());
    System.out.println("yay");
  }

  private static GeneratorProperties loadProperties() {
    return GeneratorProperties.readProperties();
  }
}
