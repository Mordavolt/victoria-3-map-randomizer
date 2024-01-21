package lv.kitn.state;

import java.util.List;
import lv.kitn.building.StateBuilding;
import lv.kitn.country.Country;
import lv.kitn.population.Population;
import lv.kitn.province.Province;

public record RegionState(
    State state,
    Country country,
    List<Province> ownedProvinces,
    List<Population> populations,
    List<StateBuilding> buildings) {}
