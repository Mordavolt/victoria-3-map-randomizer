package state;

import building.Building;
import building.BuildingGroup;
import culture.Culture;
import java.util.List;
import java.util.Map;
import province.Province;

public record State(
    String variableName,
    String id,
    List<Culture> homelandCultures,
    Building building,
    List<Province> provinces,
    Map<BuildingType, Province> buildings,
    Integer arableLand,
    List<BuildingGroup> arableResources,
    Map<BuildingGroup, Integer> cappedResources,
    Map<BuildingGroup, Integer> discoverableResources,
    Integer navalExitId) {}
