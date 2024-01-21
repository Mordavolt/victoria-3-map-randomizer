package lv.kitn.province;

import static com.google.common.collect.ImmutableList.toImmutableList;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import lv.kitn.pdxfile.PdxFileReader;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.springframework.stereotype.Service;

@Service
public class ProvinceLoader {
  public List<Province> loadProvinces(String filePath) {
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
        .collect(toImmutableList());
  }
}
