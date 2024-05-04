package lv.kitn.generator;

import static com.google.common.collect.ImmutableSet.toImmutableSet;

import com.google.common.collect.ImmutableSet;
import lv.kitn.pdxfile.PdxFileReader;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;

public class CultureLoader {
  public static ImmutableSet<Culture> loadCultures(String filePath) {
    try {
      return getCultures(CharStreams.fromFileName(filePath));
    } catch (Exception e) {
      throw new RuntimeException("Could not read cultures from " + filePath, e);
    }
  }

  static ImmutableSet<Culture> getCultures(CharStream charStream) {
    return PdxFileReader.parseFileToDataMap(charStream).keySet().stream()
        .map(Culture::new)
        .collect(toImmutableSet());
  }
}
