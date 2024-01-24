package lv.kitn.state;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lv.kitn.building.StateBuilding;
import lv.kitn.country.Country;
import lv.kitn.culture.Culture;

public record RegionState(
    State state,
    Country country,
    ImmutableSet<String> ownedProvinces,
    ImmutableMap<Culture, Integer> populations,
    ImmutableSet<StateBuilding> buildings) {}
