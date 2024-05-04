package lv.kitn.generator;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.Locale;
import lv.kitn.pdxfile.PdxFileReader;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;

public class ProvinceLoader {
  public static ImmutableMap<String, Terrain> loadProvinces(String filePath) {
    try {
      return getProvinces(CharStreams.fromFileName(filePath));
    } catch (Exception e) {
      throw new RuntimeException("Could not read provinces from " + filePath, e);
    }
  }

  static ImmutableMap<String, Terrain> getProvinces(CharStream charStream) throws IOException {
    var dataMap = PdxFileReader.parseFileToDataMap(charStream);
    return ImmutableMap.copyOf(
        Maps.transformValues(
            dataMap,
            v ->
                Terrain.valueOf(
                    ((String) v)
                        .toUpperCase(Locale.ROOT)
                        .substring(1, ((String) v).length() - 1))));
  }
}
