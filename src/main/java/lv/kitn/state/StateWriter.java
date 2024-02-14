package lv.kitn.state;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.groupingBy;

import com.google.common.collect.ImmutableSet;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
      result.add(String.format("\ts:%s = {", entry.getKey().variableName()));
      for (RegionState regionState : entry.getValue()) {
        result.add("\t\tcreate_state = {");
        result.add(String.format("\t\t\tcountry = c:%s", regionState.country().id()));
        StringBuilder ownedProvinces = new StringBuilder("\t\t\towned_provinces = { ");
        for (String province : regionState.ownedProvinces()) {
          ownedProvinces.append(province).append(" ");
        }
        ownedProvinces.append("}");
        result.add(ownedProvinces.toString());
        result.add("\t\t}");
      }
      for (Culture homelandCulture : entry.getKey().homelandCultures()) {
        result.add(String.format("\t\tadd_homeland = cu:%s", homelandCulture.id()));
      }
      result.add("\t}");
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
      result.add(String.format("\ts:%s = {", entry.getKey().variableName()));
      for (RegionState regionState : entry.getValue()) {
        result.add(String.format("\t\tregion_state:%s = {", regionState.country().id()));
        for (Population population : regionState.populations()) {
          result.add("\t\t\tcreate_pop = {");
          population
              .popType()
              .ifPresent(type -> result.add(String.format("\t\t\t\tpop_type = %s", type)));
          result.add(String.format("\t\t\t\tculture = %s", population.culture().id()));
          population
              .religion()
              .ifPresent(religion -> result.add(String.format("\t\t\t\treligion = %s", religion)));
          result.add(String.format("\t\t\t\tsize = %d", population.size()));
          result.add("\t\t\t}");
        }
        result.add("\t\t}");
      }
      result.add("\t}");
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
      result.add(String.format("\ts:%s = {", entry.getKey().variableName()));
      for (RegionState regionState : entry.getValue()) {
        result.add(String.format("\t\tregion_state:%s = {", regionState.country().id()));
        for (StateBuilding stateBuilding : regionState.buildings()) {
          result.add("\t\t\tcreate_building = {");
          result.add(String.format("\t\t\t\tbuilding = \"%s\"", stateBuilding.building().id()));
          result.add(String.format("\t\t\t\tlevel = %d", stateBuilding.level()));
          result.add(String.format("\t\t\t\treserves = %d", stateBuilding.reserves()));

          StringBuilder productionMethods =
              new StringBuilder("\t\t\t\tactivate_production_methods = { ");
          for (ProductionMethodGroup productionMethodGroup :
              stateBuilding.activateProductionMethods()) {
            productionMethods.append("\"").append(productionMethodGroup.id()).append("\" ");
          }
          productionMethods.append("}");
          result.add(productionMethods.toString());
          result.add("\t\t\t}");
        }
        result.add("\t\t}");
      }
      result.add("\t}");
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
      result.add(String.format("%s = {", strategicRegion.name()));

      result.add(String.format("\tgraphical_culture = \"%s\"", strategicRegion.graphicalCulture()));
      result.add(String.format("\tcapital_province = %s", strategicRegion.capitalProvince()));
      result.add(
          String.format(
              "\tmap_color = { %.3f %.3f %.3f }",
              strategicRegion.mapColor().red(),
              strategicRegion.mapColor().green(),
              strategicRegion.mapColor().blue()));

      StringBuilder productionMethods = new StringBuilder("\tstates = { ");
      for (State state : strategicRegion.states()) {
        productionMethods.append(state.variableName()).append(" ");
      }
      productionMethods.append("}");
      result.add(productionMethods.toString());
      result.add("}");
    }

    return result;
  }
}
