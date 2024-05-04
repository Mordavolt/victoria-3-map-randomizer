package lv.kitn.generator;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import java.util.Optional;

public record State(
    String variableName,
    Integer id,
    ImmutableList<Culture> homelandCultures,
    Building substinenceBuilding,
    ImmutableSet<String> provinces,
    ImmutableSet<String> impassable,
    ImmutableSet<String> primeLand,
    ImmutableSet<String> traits,
    ImmutableMap<BuildingType, String> buildings,
    Integer arableLand,
    ImmutableSet<String> arableResources,
    ImmutableMap<String, Integer> cappedResources,
    ImmutableSet<DiscoverableResource> discoverableResources,
    Optional<Integer> navalExitId) {}
