package lv.kitn.generator;

import com.google.common.collect.ImmutableList;

public record StateBuilding(
    Building building,
    Integer level,
    Integer reserves,
    ImmutableList<ProductionMethodGroup> activateProductionMethods) {}
