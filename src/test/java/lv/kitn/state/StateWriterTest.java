package lv.kitn.state;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import java.util.Optional;
import lv.kitn.country.Country;
import lv.kitn.culture.Culture;
import org.junit.jupiter.api.Test;

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
            Optional.empty());
    var regionState1 =
        new RegionState(
            state,
            new Country("JOH"),
            ImmutableSet.of("x76546D", "x0080B0"),
            ImmutableMap.of(),
            ImmutableSet.of());
    var regionState2 =
        new RegionState(
            state,
            new Country("SIA"),
            ImmutableSet.of("xF9F87A", "x80C0B0", "xCAB9F2"),
            ImmutableMap.of(),
            ImmutableSet.of());

    var strings = StateWriter.serialiseHistoryStates(ImmutableSet.of(regionState1, regionState2));

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
