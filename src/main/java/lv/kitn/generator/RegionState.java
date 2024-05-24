package lv.kitn.generator;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

public record RegionState(
    State state,
    String countryId,
    ImmutableSet<String> ownedProvinces,
    ImmutableMap<Population, Integer> populations,
    ImmutableSet<StateBuilding> buildings) {}
