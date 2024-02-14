package lv.kitn.state;

import com.google.common.collect.ImmutableSet;

public record StrategicRegion(
    String name,
    String graphicalCulture,
    String capitalProvince,
    Color mapColor,
    ImmutableSet<State> states) {}
