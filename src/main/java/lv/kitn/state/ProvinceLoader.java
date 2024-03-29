package lv.kitn.state;

import static com.google.common.collect.ImmutableSet.toImmutableSet;

import java.io.IOException;
import java.util.Locale;
import java.util.Set;
import lv.kitn.pdxfile.PdxFileReader;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.springframework.stereotype.Service;

@Service
public class ProvinceLoader {
  public Set<Province> loadProvinces(String filePath) {
    try {
      return getProvinces(CharStreams.fromFileName(filePath));
    } catch (Exception e) {
      throw new RuntimeException("Could not read provinces from " + filePath, e);
    }
  }

  static Set<Province> getProvinces(CharStream charStream) throws IOException {
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
        .collect(toImmutableSet());
  }
}
