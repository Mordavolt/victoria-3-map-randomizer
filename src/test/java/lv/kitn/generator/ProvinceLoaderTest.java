package lv.kitn.generator;

import static lv.kitn.generator.Terrain.DESERT;
import static lv.kitn.generator.Terrain.PLAINS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import java.io.StringReader;
import org.antlr.v4.runtime.CharStreams;
import org.junit.jupiter.api.Test;

final class ProvinceLoaderTest {
  @Test
  void loadProvinces() throws Exception {
    var input =
        """
            #This is a generated file, do not modify unless you know what you are doing!
            x48E2A5="desert"
            x5011E0="plains"
            xC09060="plains"
            """;
    var charStream = CharStreams.fromReader(new StringReader(input));

    var provinces = ProvinceLoader.getProvinces(charStream);

    assertThat(provinces)
        .contains(entry("x48E2A5", DESERT), entry("x5011E0", PLAINS), entry("xC09060", PLAINS));
  }
}
