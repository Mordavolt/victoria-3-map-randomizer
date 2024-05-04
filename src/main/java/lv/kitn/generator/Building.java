package lv.kitn.generator;

import com.google.common.collect.ImmutableList;

public record Building(
    String id,
    String buildingGroup,
    ImmutableList<ProductionMethodGroup> productionMethodGroups) {}
