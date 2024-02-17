package lv.kitn.state;

import static com.google.common.collect.ImmutableSet.toImmutableSet;
import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.groupingBy;

import com.google.common.collect.ImmutableSet;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StateWriter {
  private static final Logger LOG = LoggerFactory.getLogger(StateWriter.class);

  public static void writeHistoryStates(ImmutableSet<RegionState> regionStates, String filePath) {
    LOG.debug("Writing history states to {}", filePath);
    try {
      new File(filePath).getParentFile().mkdirs();
      BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, UTF_8));
      writer.write('\ufeff');
      writer.write("STATES = {");
      writer.newLine();
      for (String string : serialiseHistoryStates(regionStates)) {
        writer.write(string);
        writer.newLine();
      }
      writer.write("}");
      writer.newLine();
      writer.close();
    } catch (Exception e) {
      throw new RuntimeException("Could not write history states to " + filePath, e);
    }
  }

  static List<String> serialiseHistoryStates(ImmutableSet<RegionState> regionStates)
      throws IOException {
    var result = new ArrayList<String>();
    var stateToRegions = regionStates.stream().collect(groupingBy(RegionState::state));
    for (Map.Entry<State, List<RegionState>> entry : stateToRegions.entrySet()) {
      result.add(format("  s:%s = {", entry.getKey().variableName()));
      for (RegionState regionState : entry.getValue()) {
        result.add("    create_state = {");
        result.add(format("      country = c:%s", regionState.country().id()));
        result.add(serialiseListOfStrings("owned_provinces", regionState.ownedProvinces(), 6));
        result.add("    }");
      }
      for (Culture homelandCulture : entry.getKey().homelandCultures()) {
        result.add(format("    add_homeland = cu:%s", homelandCulture.id()));
      }
      result.add("  }");
    }
    return result;
  }

  public static void writeHistoryPops(ImmutableSet<RegionState> regionStates, String filePath) {
    LOG.debug("Writing history pops to {}", filePath);
    try {
      new File(filePath).getParentFile().mkdirs();
      BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, UTF_8));
      writer.write('\ufeff');
      writer.write("POPS = {");
      writer.newLine();
      for (String string : serialiseHistoryPops(regionStates)) {
        writer.write(string);
        writer.newLine();
      }
      writer.write("}");
      writer.newLine();
      writer.close();
    } catch (Exception e) {
      throw new RuntimeException("Could not write history pops to " + filePath, e);
    }
  }

  static List<String> serialiseHistoryPops(ImmutableSet<RegionState> regionStates)
      throws IOException {
    var result = new ArrayList<String>();
    var stateToRegions = regionStates.stream().collect(groupingBy(RegionState::state));
    for (Map.Entry<State, List<RegionState>> entry : stateToRegions.entrySet()) {
      result.add(format("  s:%s = {", entry.getKey().variableName()));
      for (RegionState regionState : entry.getValue()) {
        result.add(format("    region_state:%s = {", regionState.country().id()));
        for (Population population : regionState.populations()) {
          result.add("      create_pop = {");
          population.popType().ifPresent(type -> result.add(format("        pop_type = %s", type)));
          result.add(format("        culture = %s", population.culture().id()));
          population
              .religion()
              .ifPresent(religion -> result.add(format("        religion = %s", religion)));
          result.add(format("        size = %d", population.size()));
          result.add("      }");
        }
        result.add("    }");
      }
      result.add("  }");
    }
    return result;
  }

  public static void writeHistoryBuildings(
      ImmutableSet<RegionState> regionStates, String filePath) {
    LOG.debug("Writing history buildings to {}", filePath);
    try {
      new File(filePath).getParentFile().mkdirs();
      BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, UTF_8));
      writer.write('\ufeff');
      writer.write("BUILDINGS = {");
      writer.newLine();
      for (String string : serialiseHistoryBuildings(regionStates)) {
        writer.write(string);
        writer.newLine();
      }
      writer.write("}");
      writer.newLine();
      writer.close();
    } catch (Exception e) {
      throw new RuntimeException("Could not write history buildings to " + filePath, e);
    }
  }

  static List<String> serialiseHistoryBuildings(ImmutableSet<RegionState> regionStates)
      throws IOException {
    var result = new ArrayList<String>();
    var stateToRegions = regionStates.stream().collect(groupingBy(RegionState::state));
    for (Map.Entry<State, List<RegionState>> entry : stateToRegions.entrySet()) {
      result.add(format("  s:%s = {", entry.getKey().variableName()));
      for (RegionState regionState : entry.getValue()) {
        result.add(format("    region_state:%s = {", regionState.country().id()));
        for (StateBuilding stateBuilding : regionState.buildings()) {
          result.add("      create_building = {");
          result.add(format("        building = \"%s\"", stateBuilding.building().id()));
          result.add(format("        level = %d", stateBuilding.level()));
          result.add(format("        reserves = %d", stateBuilding.reserves()));
          result.add(
              serialiseListOfStrings(
                  "activate_production_methods",
                  stateBuilding.activateProductionMethods().stream()
                      .map(ProductionMethodGroup::id)
                      .collect(toImmutableSet()),
                  8));
          result.add("      }");
        }
        result.add("    }");
      }
      result.add("  }");
    }
    return result;
  }

  public static void writeStrategicRegions(
      ImmutableSet<StrategicRegion> strategicRegions, String filePath) {
    LOG.debug("Writing strategic regions to {}", filePath);
    try {
      new File(filePath).getParentFile().mkdirs();
      BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, UTF_8));
      writer.write('\ufeff');
      for (String string : serialiseStrategicRegions(strategicRegions)) {
        writer.write(string);
        writer.newLine();
      }
      writer.close();
    } catch (Exception e) {
      throw new RuntimeException("Could not write strategic regions to " + filePath, e);
    }
  }

  static List<String> serialiseStrategicRegions(ImmutableSet<StrategicRegion> strategicRegions)
      throws IOException {
    var result = new ArrayList<String>();
    for (StrategicRegion strategicRegion : strategicRegions) {
      result.add(format("%s = {", strategicRegion.name()));

      result.add(format("  graphical_culture = \"%s\"", strategicRegion.graphicalCulture()));
      result.add(format("  capital_province = %s", strategicRegion.capitalProvince()));
      result.add(
          format(
              "  map_color = { %.3f %.3f %.3f }",
              strategicRegion.mapColor().red(),
              strategicRegion.mapColor().green(),
              strategicRegion.mapColor().blue()));

      result.add(
          serialiseListOfStrings(
              "states",
              strategicRegion.states().stream().map(State::variableName).collect(toImmutableSet()),
              2));
      result.add("}");
    }

    return result;
  }

  public static void writeStateRegions(ImmutableSet<State> states, String filePath) {
    LOG.debug("Writing state regions to {}", filePath);
    try {
      new File(filePath).getParentFile().mkdirs();
      BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, UTF_8));
      writer.write('\ufeff');
      for (String string : serialiseStateRegions(states)) {
        writer.write(string);
        writer.newLine();
      }
      writer.close();
    } catch (Exception e) {
      throw new RuntimeException("Could not write state regions to " + filePath, e);
    }
  }

  static List<String> serialiseStateRegions(ImmutableSet<State> states) throws IOException {
    var result = new ArrayList<String>();
    for (State state : states) {
      result.add(format("%s = {", state.variableName()));

      result.add(format("  id = %s", state.id()));
      result.add(format("  subsistence_building = \"%s\"", state.substinenceBuilding().id()));
      result.add(serialiseListOfStrings("provinces", state.provinces(), 2));
      if (!state.impassable().isEmpty()) {
        result.add(serialiseListOfStrings("impassable", state.impassable(), 2));
      }
      if (!state.primeLand().isEmpty()) {
        result.add(serialiseListOfStrings("prime_land", state.primeLand(), 2));
      }
      if (!state.traits().isEmpty()) {
        result.add(serialiseListOfStrings("traits", state.traits(), 2));
      }
      for (Map.Entry<BuildingType, String> building : state.buildings().entrySet()) {
        result.add(
            format(
                "  %s = \"%s\"",
                building.getKey().name().toLowerCase(Locale.ROOT), building.getValue()));
      }
      result.add(format("  arable_land = %d", state.arableLand()));
      result.add(
          serialiseListOfStrings(
              "arable_resources",
              state.arableResources().stream().map(BuildingGroup::id).collect(toImmutableSet()),
              2));
      result.add("  capped_resources = {");
      for (Map.Entry<BuildingGroup, Integer> building : state.cappedResources().entrySet()) {
        result.add(format("    %s = %d", building.getKey().id(), building.getValue()));
      }
      result.add("  }");
      for (DiscoverableResource discoverableResource : state.discoverableResources()) {
        result.add("  resource = {");
        result.add(format("    type = \"%s\"", discoverableResource.type().id()));
        discoverableResource
            .depletedType()
            .map(BuildingGroup::id)
            .ifPresent(type -> result.add(format("    depleted_type = \"%s\"", type)));
        result.add(
            format("    undiscovered_amount = %d", discoverableResource.undiscoveredAmount()));
        discoverableResource
            .discoveredAmount()
            .ifPresent(amount -> result.add(format("    discovered_amount = %d", amount)));
        result.add("  }");
      }

      state
          .navalExitId()
          .ifPresent(navalExit -> result.add(format("  naval_exit_id = %d", navalExit)));
      ;

      result.add("}");
    }

    return result;
  }

  private static String serialiseListOfStrings(
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
