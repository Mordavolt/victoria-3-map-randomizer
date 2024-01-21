package state;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.collect.ImmutableList;
import country.Country;
import culture.Culture;
import org.junit.jupiter.api.Test;
import province.Province;

final class StateWriterTest {

  @Test
  void serialiseHistoryStates() throws Exception {
    var state =
        new State(
            "STATE_MALAYA",
            null,
            ImmutableList.of(new Culture("malay")),
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
            ImmutableList.of(new Province("x76546D"), new Province("x0080B0")),
            ImmutableList.of(),
            ImmutableList.of());
    var regionState2 =
        new RegionState(
            state,
            new Country("SIA"),
            ImmutableList.of(
                new Province("xF9F87A"), new Province("x80C0B0"), new Province("xCAB9F2")),
            ImmutableList.of(),
            ImmutableList.of());

    var strings = StateWriter.serialiseHistoryStates(ImmutableList.of(regionState1, regionState2));

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
