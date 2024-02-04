package lv.kitn.state;

import com.google.common.collect.ImmutableList;

public record Building(
    String id,
    BuildingGroup buildingGroup,
    ImmutableList<ProductionMethodGroup> productionMethodGroups) {}
