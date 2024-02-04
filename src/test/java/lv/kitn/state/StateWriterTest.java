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
            "STATE_MALAYA", null, null, null, null, null, null, null, null, null, Optional.empty());
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

  @Test
  void serialiseHistoryBuildings() throws Exception {
    var state =
        new State(
            "STATE_MALAYA", null, null, null, null, null, null, null, null, null, Optional.empty());
    var regionState1 =
        new RegionState(
            state,
            new Country("JOH"),
            ImmutableSet.of(),
            ImmutableSet.of(),
            ImmutableSet.of(
                new StateBuilding(
                    new Building("building_fishing_wharf", null, null),
                    1,
                    1,
                    ImmutableList.of(
                        new ProductionMethodGroup("pm_simple_fishing"),
                        new ProductionMethodGroup("pm_unrefrigerated"),
                        new ProductionMethodGroup("pm_merchant_guilds_building_fishing_wharf")))));
    var regionState2 =
        new RegionState(
            state,
            new Country("SEL"),
            ImmutableSet.of(),
            ImmutableSet.of(),
            ImmutableSet.of(
                new StateBuilding(
                    new Building("building_barracks", null, null),
                    5,
                    1,
                    ImmutableList.of(new ProductionMethodGroup("pm_no_organization"))),
                new StateBuilding(
                    new Building("building_rice_farm", null, null),
                    1,
                    100,
                    ImmutableList.of(
                        new ProductionMethodGroup("pm_simple_farming_building_rice_farm"),
                        new ProductionMethodGroup("pm_no_secondary"),
                        new ProductionMethodGroup("pm_tools_disabled"),
                        new ProductionMethodGroup("pm_privately_owned")))));

    var strings =
        StateWriter.serialiseHistoryBuildings(ImmutableSet.of(regionState1, regionState2));

    assertThat(String.join("\n", strings) + "\n")
        .isEqualTo(
            """
	s:STATE_MALAYA = {
		region_state:JOH = {
			create_building = {
				building = "building_fishing_wharf"
				level = 1
				reserves = 1
				activate_production_methods = { "pm_simple_fishing" "pm_unrefrigerated" "pm_merchant_guilds_building_fishing_wharf" }
			}
		}
		region_state:SEL = {
			create_building = {
				building = "building_barracks"
				level = 5
				reserves = 1
				activate_production_methods = { "pm_no_organization" }
			}
			create_building = {
				building = "building_rice_farm"
				level = 1
				reserves = 100
				activate_production_methods = { "pm_simple_farming_building_rice_farm" "pm_no_secondary" "pm_tools_disabled" "pm_privately_owned" }
			}
		}
	}
""");
  }
}
