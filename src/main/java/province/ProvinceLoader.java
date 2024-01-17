package province;

import static java.util.stream.Collectors.toList;

import file_reader.PdxFileReader;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;

public class ProvinceLoader {
  public static List<Province> loadProvinces(String filePath) {
    try {
      return getProvinces(CharStreams.fromFileName(filePath));
    } catch (Exception e) {
      throw new RuntimeException("Could not read provinces from " + filePath, e);
    }
  }

  static List<Province> getProvinces(CharStream charStream) throws IOException {
    var dataMap = PdxFileReader.parseFileToDataMap(charStream);
    return dataMap.entrySet().stream()
        .map(
            entry -> {
              var id = entry.getKey();
              var terrainString = (String) entry.getValue();
              var terrain =
                  terrainString.toUpperCase(Locale.ROOT).substring(1, terrainString.length() - 1);
              return new Province(id, Terrain.valueOf(terrain));
            })
        .collect(toList());
  }
}
