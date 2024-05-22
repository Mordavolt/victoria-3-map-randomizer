package lv.kitn.generator;

import static com.google.common.collect.ImmutableSet.toImmutableSet;
import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;

import com.google.common.collect.ImmutableSet;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CountryWriter {
  private static final Logger LOG = LoggerFactory.getLogger(CountryWriter.class);

  public static void writeHistoryCountries(ImmutableSet<Country> countries, String filePath) {
    LOG.debug("Writing history countries to {}", filePath);
    try {
      new File(filePath).getParentFile().mkdirs();
      BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, UTF_8));
      writer.write('\ufeff');
      writer.write("COUNTRIES = {");
      writer.newLine();
      for (String string : serializeHistoryCountries(countries)) {
        writer.write(string);
        writer.newLine();
      }
      writer.write("}");
      writer.newLine();
      writer.close();
    } catch (Exception e) {
      throw new RuntimeException("Could not write history countries to " + filePath, e);
    }
  }

  static List<String> serializeHistoryCountries(ImmutableSet<Country> countries) {
    var result = new ArrayList<String>();
    for (Country country : countries) {
      result.add(format("  c:%s = {", country.id()));
      for (var law : country.activatedLaws()) {
        result.add(format("    activate_law = law_type:%s", law.id()));
      }
      for (var good : country.taxedGoods()) {
        result.add(format("    add_taxed_goods = g:%s", good.id()));
      }
      for (var technology : country.researchedTechnologies()) {
        result.add(format("    add_technology_researched = %s", technology.id()));
      }
      result.add(
          format(
              "    effect_starting_politics_%s = yes",
              country.startingPolitics().name().toLowerCase(Locale.ROOT)));
      result.add(
          format("    effect_starting_technology_tier_%d_tech = yes", country.technologyTier()));
      country
          .nextElectionDate()
          .ifPresent(
              date -> result.add(format("    set_next_election_date = %s", formatDate(date))));
      for (var entry : country.tariffs().entrySet()) {
        result.add(
            format(
                "    set_tariffs_%s_priority = g:%s",
                entry.getValue() ? "import" : "export", entry.getKey().id()));
      }
      result.add(format("    set_tax_level = %s", getTaxLevel(country.taxLevel())));
      for (var entry : country.institutionInvestmentLevels().entrySet()) {
        result.add("    set_institution_investment_level = {");
        result.add(format("        institution = %s", entry.getKey().id()));
        result.add(format("        level = %s", entry.getValue()));
        result.add("    }");
      }
      for (var interestGroup : country.rulingInterestGroups()) {
        result.add(format("    ig:%s = {", interestGroup.id()));
        result.add("        add_ruling_interest_group = yes");
        result.add("    }");
      }
      for (var entry : country.companies().entrySet()) {
        result.add(format("    add_company = company_type:%s", entry.getKey().id()));
        result.add(format("    company:%s = {", entry.getKey().id()));
        result.add(
            format("        set_company_establishment_date = %s", formatDate(entry.getValue())));
        result.add("    }");
      }
      for (var entry : country.variables().entrySet()) {
        result.add("    set_variable = {");
        result.add(format("        name = %s", entry.getKey()));
        result.add(format("        value = %s", entry.getValue()));
        result.add("    }");
      }
      for (var journalEntry : country.journalEntries()) {
        result.add("    add_journal_entry = {");
        result.add(format("        type = %s", journalEntry));
        result.add("    }");
      }
      for (var entry : country.modifiers().entrySet()) {
        result.add("    add_modifier = {");
        result.add(format("        name = %s", entry.getKey()));
        result.add(format("        months = %s", entry.getValue()));
        result.add("    }");
      }
      result.add("  }");
    }
    return result;
  }

  public static void writeCountryDefinitions(ImmutableSet<Country> countries, String filePath) {
    LOG.debug("Writing country definitions to {}", filePath);
    try {
      new File(filePath).getParentFile().mkdirs();
      BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, UTF_8));
      writer.write('\ufeff');
      for (String string : serializeCountryDefinitions(countries)) {
        writer.write(string);
        writer.newLine();
      }
      writer.newLine();
      writer.close();
    } catch (Exception e) {
      throw new RuntimeException("Could not write country definitions to " + filePath, e);
    }
  }

  static List<String> serializeCountryDefinitions(ImmutableSet<Country> countries) {
    var result = new ArrayList<String>();
    for (Country country : countries) {
      result.add(format("%s = {", country.id()));
      result.add(
          format(
              "  color = { %.3f %.3f %.3f }",
              country.mapColor().red(), country.mapColor().green(), country.mapColor().blue()));
      result.add(
          format("  country_type = %s", country.countryType().name().toLowerCase(Locale.ROOT)));
      result.add(format("  tier = %s", country.tier().name().toLowerCase(Locale.ROOT)));
      result.add(
          serializeListOfStrings(
              "cultures",
              country.cultures().stream()
                  .map(culture -> culture.name().toLowerCase(Locale.ROOT))
                  .collect(toImmutableSet()),
              2));
      result.add(format("  capital = %s", country.capitalState()));
      result.add("}");
    }
    return result;
  }

  public static void writeCountryLocalizations(
      ImmutableSet<CountryLocalization> countryLocalizations, String filePath) {
    LOG.debug("Writing country localizations to {}", filePath);
    try {
      new File(filePath).getParentFile().mkdirs();
      BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, UTF_8));
      writer.write('\ufeff');
      writer.write("l_english:");
      writer.newLine();
      for (String string : serializeCountryLocalizations(countryLocalizations)) {
        writer.write(string);
        writer.newLine();
      }
      writer.newLine();
      writer.close();
    } catch (Exception e) {
      throw new RuntimeException("Could not write country localizations to " + filePath, e);
    }
  }

  static List<String> serializeCountryLocalizations(
      ImmutableSet<CountryLocalization> countryLocalizations) {
    var result = new ArrayList<String>();
    for (CountryLocalization countryLocalization : countryLocalizations) {
      result.add(format("  %s:0 \"%s\"", countryLocalization.id(), countryLocalization.name()));
      result.add(
          format("  %s_ADJ:0 \"%s\"", countryLocalization.id(), countryLocalization.adjective()));
    }
    return result;
  }

  private static String getTaxLevel(Integer level) {
    return switch (level) {
      case 1 -> "low";
      case 2 -> "medium";
      case 3 -> "high";
      default -> throw new RuntimeException("Tax level must be betwee 1 and 3");
    };
  }

  private static String formatDate(LocalDate date) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.M.d");
    return date.format(formatter);
  }

  private static String serializeListOfStrings(
      String key, ImmutableSet<String> strings, int indentation) {
    StringBuilder result = new StringBuilder();
    result.append(" ".repeat(indentation));
    result.append(format("%s = { ", key));
    for (String string : strings) {
      result.append("\"").append(string).append("\" ");
    }
    result.append("}");
    return result.toString();
  }
}
