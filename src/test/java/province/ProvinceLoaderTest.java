package province;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.StringReader;
import org.antlr.v4.runtime.CharStreams;
import org.junit.jupiter.api.Test;

final class ProvinceLoaderTest {
  @Test
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
            new Province("x48E2A5"),
            new Province("x5011E0"),
            new Province("xC09060"));
  }
}
