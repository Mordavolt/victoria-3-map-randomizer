package lv.kitn.state;

import java.util.List;
import java.util.Map;
import lv.kitn.building.Building;
import lv.kitn.building.BuildingGroup;
import lv.kitn.culture.Culture;
import lv.kitn.province.Province;

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
