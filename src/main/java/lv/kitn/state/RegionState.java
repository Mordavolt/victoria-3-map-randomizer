package lv.kitn.state;

import com.google.common.collect.ImmutableSet;

public record RegionState(
    State state,
    Country country,
    ImmutableSet<String> ownedProvinces,
    ImmutableSet<Population> populations,
    ImmutableSet<StateBuilding> buildings) {}
