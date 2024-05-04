package lv.kitn.generator;

import com.google.common.collect.ImmutableMap;

record ProvincePrefab(
    String id,
    Terrain terrain,
    boolean coastal,
    ImmutableMap<Population, Integer> populations,
    ImmutableMap<String, Integer> cappedResources,
    ImmutableMap<String, Integer> discoverableResources) {}
