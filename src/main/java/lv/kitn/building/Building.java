package lv.kitn.building;

import com.google.common.collect.ImmutableList;

public record Building(
    String id,
    BuildingGroup buildingGroup,
    ImmutableList<ProductionMethodGroup> productionMethodGroups) {}
