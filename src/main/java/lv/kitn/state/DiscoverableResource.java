package lv.kitn.state;

import java.util.Optional;

public record DiscoverableResource(
    BuildingGroup type,
    Optional<BuildingGroup> depletedType,
    Integer undiscoveredAmount,
    Optional<Integer> discoveredAmount) {}
