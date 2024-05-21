package lv.kitn.generator;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.StringReader;
import org.antlr.v4.runtime.CharStreams;
import org.junit.jupiter.api.Test;

final class SeaLoaderTest {
  @Test
  void getSeas() throws Exception {
    var input =
        """
STATE_PACIFIC_LANE_05 = {
    id = 3012
    provinces = { "xA63565" }
}

STATE_PACIFIC_LANE_06 = {
    id = 3013
    provinces = { "x982455" }
}

STATE_SOUTHERN_OCEAN_01 = {
    id = 3014
    provinces = { "x6FB3DE" }
    impassable = { "x6FB3DE" }
}

STATE_FAROE_BANK = {
    id = 3016
    provinces = { "x709ACF" }
}
        """;

    var charStream = CharStreams.fromReader(new StringReader(input));
    var seas = SeaLoader.getSeas(charStream);

    assertThat(seas)
        .contains(
            new Sea("STATE_PACIFIC_LANE_05", 3012, "xA63565"),
            new Sea("STATE_PACIFIC_LANE_06", 3013, "x982455"),
            new Sea("STATE_FAROE_BANK", 3016, "x709ACF"));
  }
}
