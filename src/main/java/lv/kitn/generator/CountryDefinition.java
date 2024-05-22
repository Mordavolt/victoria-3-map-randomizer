package lv.kitn.generator;

import com.google.common.collect.ImmutableSet;

public record CountryDefinition(
    String id,
    // It's decimal, but values are 1.0 - 255.0 FML
    Color mapColor,
    CountryType countryType,
    CountryTier tier,
    ImmutableSet<Culture> cultures,
    String capitalState) {}
