package province;

import static org.assertj.core.api.Assertions.assertThat;
import static province.Terrain.DESERT;
import static province.Terrain.PLAINS;

import java.io.StringReader;
import org.antlr.v4.runtime.CharStreams;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProvinceLoaderTest {
  @Test
  @DisplayName("Should be able to load provinces with terrain")
  void loadProvinces() throws Exception {
    // Given
    var input =
        """
            #This is a generated file, do not modify unless you know what you are doing!
            x48E2A5="desert"
            x5011E0="plains"
            xC09060="plains"
            """;
    var charStream = CharStreams.fromReader(new StringReader(input));

    // When
    var provinces = ProvinceLoader.getProvinces(charStream);

    // Then
    assertThat(provinces)
        .contains(
            new Province("x48E2A5", DESERT),
            new Province("x5011E0", PLAINS),
            new Province("xC09060", PLAINS));
  }
}
