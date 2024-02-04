package lv.kitn.state;

import com.google.common.collect.ImmutableSet;
import lv.kitn.building.StateBuilding;
import lv.kitn.country.Country;

public record RegionState(
    State state,
    Country country,
    ImmutableSet<String> ownedProvinces,
    ImmutableSet<Population> populations,
    ImmutableSet<StateBuilding> buildings) {}
