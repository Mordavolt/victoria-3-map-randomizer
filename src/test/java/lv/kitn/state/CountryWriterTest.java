package lv.kitn.state;

import static lv.kitn.state.Politics.CONSERVATIVE;
import static lv.kitn.state.Politics.TRADITIONAL;
import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Test;

final class CountryWriterTest {

  @Test
  void serialiseHistoryStates() throws Exception {
    var country1 =
        new Country(
            "AAA",
            ImmutableSet.of(
                new Law("law_appointed_bureaucrats"),
                new Law("law_autocracy"),
                new Law("law_censorship")),
            ImmutableSet.of(new Good("liquor"), new Good("luxury_clothes")),
            ImmutableSet.of(
                new Technology("atmospheric_engine"), new Technology("corporate_charters")),
            CONSERVATIVE,
            5,
            Optional.of(LocalDate.of(1836, 9, 29)),
            ImmutableMap.of(new Good("grain"), false, new Good("fabric"), true),
            2,
            ImmutableMap.of(
                new Institution("institution_schools"),
                1,
                new Institution("institution_colonial_affairs"),
                3),
            ImmutableSet.of(
                new InterestGroup("ig_intelligentsia"), new InterestGroup("ig_industrialists")),
            ImmutableMap.of(
                new Company("company_john_cockerill"),
                LocalDate.of(1825, 1, 1),
                new Company("company_east_india_company"),
                LocalDate.of(1600, 12, 31)),
            ImmutableMap.of("east_indies_revolt_var", 0),
            ImmutableSet.of("je_consolidate_colonial_rule"),
            ImmutableMap.of("brazilian_slave_trade_modifier", 600));
    var country2 =
        new Country(
            "BER",
            ImmutableSet.of(new Law("law_serfdom"), new Law("law_debt_slavery")),
            ImmutableSet.of(),
            ImmutableSet.of(),
            TRADITIONAL,
            4,
            Optional.empty(),
            ImmutableMap.of(),
            3,
            ImmutableMap.of(),
            ImmutableSet.of(),
            ImmutableMap.of(),
            ImmutableMap.of(),
            ImmutableSet.of(),
            ImmutableMap.of());
    var strings = CountryWriter.serialiseHistoryCountries(ImmutableSet.of(country1, country2));

    assertThat(String.join("\n", strings) + "\n")
        .isEqualTo(
            """
  c:AAA = {
    activate_law = law_type:law_appointed_bureaucrats
    activate_law = law_type:law_autocracy
    activate_law = law_type:law_censorship
    add_taxed_goods = g:liquor
    add_taxed_goods = g:luxury_clothes
    add_technology_researched = atmospheric_engine
    add_technology_researched = corporate_charters
    effect_starting_politics_conservative = yes
    effect_starting_technology_tier_5_tech = yes
    set_next_election_date = 1836.9.29
    set_tariffs_export_priority = g:grain
    set_tariffs_import_priority = g:fabric
    set_tax_level = medium
    set_institution_investment_level = {
        institution = institution_schools
        level = 1
    }
    set_institution_investment_level = {
        institution = institution_colonial_affairs
        level = 3
    }
    ig:ig_intelligentsia = {
        add_ruling_interest_group = yes
    }
    ig:ig_industrialists = {
        add_ruling_interest_group = yes
    }
    add_company = company_type:company_john_cockerill
    company:company_john_cockerill = {
        set_company_establishment_date = 1825.1.1
    }
    add_company = company_type:company_east_india_company
    company:company_east_india_company = {
        set_company_establishment_date = 1600.12.31
    }
    set_variable = {
        name = east_indies_revolt_var
        value = 0
    }
    add_journal_entry = {
        type = je_consolidate_colonial_rule
    }
    add_modifier = {
        name = brazilian_slave_trade_modifier
        months = 600
    }
  }
  c:BER = {
    activate_law = law_type:law_serfdom
    activate_law = law_type:law_debt_slavery
    effect_starting_politics_traditional = yes
    effect_starting_technology_tier_4_tech = yes
    set_tax_level = high
  }
""");
  }
}
