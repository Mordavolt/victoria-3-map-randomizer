package lv.kitn.generator;

import java.util.List;
import java.util.Map;
import lv.kitn.pdxfile.PdxFileReader;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;

public class SeaLoader {
  public static List<Sea> loadSeas(String filePath) {
    try {
      return getSeas(CharStreams.fromFileName(filePath));
    } catch (Exception e) {
      throw new RuntimeException("Could not read seas from " + filePath, e);
    }
  }

  static List<Sea> getSeas(CharStream charStream) {
    return PdxFileReader.parseFileToDataMap(charStream).entrySet().stream()
        .filter(entry -> !((Map<?, ?>) entry.getValue()).containsKey("impassable"))
        .map(
            entry -> {
              var stateId = entry.getKey();
              var id = ((Map<String, Integer>) entry.getValue()).get("id");
              var province = ((Map<String, List<String>>) entry.getValue()).get("provinces").get(0);
              return new Sea(stateId, id, province.substring(1, province.length() - 1));
            })
        .toList();
  }
}
