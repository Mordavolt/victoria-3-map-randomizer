package lv.kitn.state;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.util.Optional;
import org.checkerframework.checker.units.qual.C;
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

  @Test
  void serialiseStrategicRegions() throws Exception {
    var state1 =
        new State(
            "STATE_CAMBODIA",
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            Optional.empty());
    var state2 =
        new State(
            "STATE_MEKONG", null, null, null, null, null, null, null, null, null, Optional.empty());
    var state3 =
        new State(
            "STATE_NORTH_SUMATRA",
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            Optional.empty());
    var strategicRegion1 = new StrategicRegion(
            "region_indochina",
            "asian",
            "x5C3070",
            new Color(0.9, 0.1, 0.1),
            ImmutableSet.of(state1, state2));
    var strategicRegion2 = new StrategicRegion(
            "region_indonesia",
            "african",
            "x6ED39E",
            new Color(0.204, 0.651, 0.308),
            ImmutableSet.of(state3));

    var strings =
        StateWriter.serialiseStrategicRegions(ImmutableSet.of(strategicRegion1, strategicRegion2));

    assertThat(String.join("\n", strings) + "\n")
        .isEqualTo(
            """
region_indochina = {
	graphical_culture = "asian"
	capital_province = x5C3070
	map_color = { 0.900 0.100 0.100 }
	states = { STATE_CAMBODIA STATE_MEKONG }
}
region_indonesia = {
	graphical_culture = "african"
	capital_province = x6ED39E
	map_color = { 0.204 0.651 0.308 }
	states = { STATE_NORTH_SUMATRA }
}
        """);
  }
}
