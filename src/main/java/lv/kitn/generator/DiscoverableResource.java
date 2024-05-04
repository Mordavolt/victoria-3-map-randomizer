package lv.kitn.generator;

import java.util.Optional;

public record DiscoverableResource(
    String type,
    Optional<String> depletedType,
    Integer undiscoveredAmount,
    Optional<Integer> discoveredAmount) {}
