package lv.kitn.generator;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import java.time.LocalDate;
import java.util.Optional;

public record CountryHistory(
    String id,
    ImmutableSet<Law> activatedLaws,
    ImmutableSet<Good> taxedGoods,
    ImmutableSet<Technology> researchedTechnologies,
    Politics startingPolitics,
    Integer technologyTier,
    Optional<LocalDate> nextElectionDate,
    // true - import, false - export
    ImmutableMap<Good, Boolean> tariffs,
    // 1 - low, 2 - medium, 3 - high
    Integer taxLevel,
    ImmutableMap<Institution, Integer> institutionInvestmentLevels,
    ImmutableSet<InterestGroup> rulingInterestGroups,
    ImmutableMap<Company, LocalDate> companies,
    ImmutableMap<String, Integer> variables,
    ImmutableSet<String> journalEntries,
    // map of name to months
    ImmutableMap<String, Integer> modifiers,
    ImmutableSet<String> provinces) {}
