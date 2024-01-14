package state;

import building.StateBuilding;
import country.Country;
import java.util.List;
import population.Population;

public record RegionState(
    State state, Country country, List<Population> populations, List<StateBuilding> buildings) {}
