package state;

import building.StateBuilding;
import country.Country;
import java.util.List;
import population.Population;
import province.Province;

public record RegionState(
    State state,
    Country country,
    List<Province> ownedProvinces,
    List<Population> populations,
    List<StateBuilding> buildings) {}
