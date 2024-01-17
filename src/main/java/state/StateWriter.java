package state;

import static java.util.stream.Collectors.groupingBy;

import culture.Culture;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import province.Province;

class StateWriter {

  public static void writeHistoryStates(List<RegionState> regionStates, String filePath) {
    try {

    } catch (Exception e) {
      throw new RuntimeException("Could not read provinces from " + filePath, e);
    }
  }

  static List<String> serialiseHistoryStates(List<RegionState> regionStates) throws IOException {
    var result = new ArrayList<String>();
    var stateToRegions = regionStates.stream().collect(groupingBy(RegionState::state));
    for (Map.Entry<State, List<RegionState>> entry : stateToRegions.entrySet()) {
      result.add(String.format("\ts:%s = {", entry.getKey().variableName()));
      for (RegionState regionState : entry.getValue()) {
        result.add("\t\tcreate_state = {");
        result.add(String.format("\t\t\tcountry = c:%s", regionState.country().id()));
        StringBuilder ownedProvinces = new StringBuilder("\t\t\towned_provinces = { ");
        for (Province province : regionState.ownedProvinces()) {
          ownedProvinces.append(province.id()).append(" ");
        }
        ownedProvinces.append("}");
        result.add(ownedProvinces.toString());
        result.add("\t\t}");
      }
      for (Culture homelandCulture : entry.getKey().homelandCultures()) {
        result.add(String.format("\t\tadd_homeland = %s", homelandCulture.id()));
      }
      result.add("\t}");
    }
    return result;
  }
}
