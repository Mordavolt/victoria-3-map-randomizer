package building;

import java.util.List;

public record Building(
    String id, BuildingGroup buildingGroup, List<ProductionMethodGroup> productionMethodGroups) {}
