package lv.kitn.state;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.util.Optional;
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
            ImmutableSet.of(),
            ImmutableSet.of());
    var regionState2 =
        new RegionState(
            state,
            new Country("SIA"),
            ImmutableSet.of("xF9F87A", "x80C0B0", "xCAB9F2"),
            ImmutableSet.of(),
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

  @Test
  void serialiseHistoryPops() throws Exception {
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
            ImmutableSet.of(),
            ImmutableSet.of(
                new Population(new Culture("malay"), 399_600, Optional.empty(), Optional.empty()),
                new Population(
                    new Culture("malay"), 11_000, Optional.empty(), Optional.of("slaves"))),
            ImmutableSet.of());
    var regionState2 =
        new RegionState(
            state,
            new Country("SEL"),
            ImmutableSet.of(),
            ImmutableSet.of(
                new Population(new Culture("malay"), 209_000, Optional.empty(), Optional.empty()),
                new Population(
                    new Culture("javan"), 5_400, Optional.of("animist"), Optional.of("slaves"))),
            ImmutableSet.of());

    var strings = StateWriter.serialiseHistoryPops(ImmutableSet.of(regionState1, regionState2));

    assertThat(String.join("\n", strings) + "\n")
        .isEqualTo(
            """
	s:STATE_MALAYA = {
		region_state:JOH = {
			create_pop = {
				culture = malay
				size = 399600
			}
			create_pop = {
				pop_type = slaves
				culture = malay
				size = 11000
			}
		}
		region_state:SEL = {
			create_pop = {
				culture = malay
				size = 209000
			}
			create_pop = {
				pop_type = slaves
				culture = javan
				religion = animist
				size = 5400
			}
		}
	}
""");
  }
}
