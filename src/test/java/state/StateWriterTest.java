package state;

import static org.assertj.core.api.Assertions.assertThat;

import country.Country;
import culture.Culture;
import java.util.List;
import org.junit.jupiter.api.Test;
import province.Province;

final class StateWriterTest {

  @Test
  void serialiseHistoryStates() throws Exception {
    //
    var state =
        new State(
            "STATE_MALAYA",
            null,
            List.of(new Culture("malay")),
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null);
    var regionState1 =
        new RegionState(
            state,
            new Country("JOH"),
            List.of(new Province("x76546D"), new Province("x0080B0")),
            List.of(),
            List.of());
    var regionState2 =
        new RegionState(
            state,
            new Country("SIA"),
            List.of(new Province("xF9F87A"), new Province("x80C0B0"), new Province("xCAB9F2")),
            List.of(),
            List.of());

    // When
    var strings = StateWriter.serialiseHistoryStates(List.of(regionState1, regionState2));

    // Then
    assertThat(String.join("\n", strings) + "\n")
        .isEqualTo(
            """
	s:STATE_MALAYA = {
		create_state = {
			country = c:JOH
			owned_provinces = { x76546D x0080B0 }
		}
		create_state = {
			country = c:SIA
			owned_provinces = { xF9F87A x80C0B0 xCAB9F2 }
		}
		add_homeland = cu:malay
	}
""");
  }
}
