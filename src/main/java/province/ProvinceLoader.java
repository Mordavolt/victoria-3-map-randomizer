package province;

import java.io.IOException;
import java.util.List;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import pdxfile.PdxFileReader;

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
    return dataMap.keySet().stream().map(Province::new).toList();
  }
}
