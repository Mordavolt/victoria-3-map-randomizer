package lv.kitn.province;

import java.io.IOException;
import java.util.List;
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
    return dataMap.keySet().stream().map(Province::new).toList();
  }
}
