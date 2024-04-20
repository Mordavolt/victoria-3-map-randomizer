package lv.kitn.generator;

import static lv.kitn.generator.BuildingType.CITY;
import static lv.kitn.generator.BuildingType.FARM;
import static lv.kitn.generator.BuildingType.MINE;
import static lv.kitn.generator.BuildingType.PORT;
import static lv.kitn.generator.BuildingType.WOOD;
import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
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
            null,
            null,
            null,
            Optional.empty());
    var regionState1 =
        new RegionState(
            state,
            new Country(
                "JOH",
                null,
                null,
                null,
                null,
                null,
                Optional.empty(),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null),
            ImmutableSet.of("x76546D", "x0080B0"),
            ImmutableSet.of(),
            ImmutableSet.of());
    var regionState2 =
        new RegionState(
            state,
            new Country(
                "SIA",
                null,
                null,
                null,
                null,
                null,
                Optional.empty(),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null),
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
      owned_provinces = { "x76546D" "x0080B0" }
    }
    create_state = {
      country = c:SIA
      owned_provinces = { "xF9F87A" "x80C0B0" "xCAB9F2" }
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
            null,
            null,
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
    var regionState1 =
        new RegionState(
            state,
            new Country(
                "JOH",
                null,
                null,
                null,
                null,
                null,
                Optional.empty(),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null),
            ImmutableSet.of(),
            ImmutableSet.of(
                new Population(new Culture("malay"), 399_600, Optional.empty(), Optional.empty()),
                new Population(
                    new Culture("malay"), 11_000, Optional.empty(), Optional.of("slaves"))),
            ImmutableSet.of());
    var regionState2 =
        new RegionState(
            state,
            new Country(
                "SEL",
                null,
                null,
                null,
                null,
                null,
                Optional.empty(),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null),
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
            "STATE_MALAYA",
            null,
            null,
            null,
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
    var regionState1 =
        new RegionState(
            state,
            new Country(
                "JOH",
                null,
                null,
                null,
                null,
                null,
                Optional.empty(),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null),
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
            new Country(
                "SEL",
                null,
                null,
                null,
                null,
                null,
                Optional.empty(),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null),
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
            null,
            null,
            null,
            Optional.empty());
    var state2 =
        new State(
            "STATE_MEKONG",
            null,
            null,
            null,
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
            null,
            null,
            null,
            Optional.empty());
    var strategicRegion1 =
        new StrategicRegion(
            "region_indochina",
            "asian",
            "x5C3070",
            new Color(0.9, 0.1, 0.1),
            ImmutableSet.of(state1, state2));
    var strategicRegion2 =
        new StrategicRegion(
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
  states = { "STATE_CAMBODIA" "STATE_MEKONG" }
}
region_indonesia = {
  graphical_culture = "african"
  capital_province = x6ED39E
  map_color = { 0.204 0.651 0.308 }
  states = { "STATE_NORTH_SUMATRA" }
}
        """);
  }

  @Test
  void serialiseStateRegions() throws Exception {
    var state1 =
        new State(
            "STATE_MALAYA",
            540,
            null,
            new Building("building_subsistence_rice_paddies", null, null),
            ImmutableSet.of("x0080B0", "x08673B"),
            ImmutableSet.of(),
            ImmutableSet.of(),
            ImmutableSet.of(),
            ImmutableMap.of(
                CITY, "x8E3305",
                PORT, "xC00130",
                FARM, "x76546D",
                MINE, "x08673B",
                WOOD, "xCAB9F2"),
            35,
            ImmutableSet.of(
                new BuildingGroup("bg_rice_farms"),
                new BuildingGroup("bg_livestock_ranches"),
                new BuildingGroup("bg_coffee_plantations"),
                new BuildingGroup("bg_tea_plantations"),
                new BuildingGroup("bg_sugar_plantations"),
                new BuildingGroup("bg_banana_plantations")),
            ImmutableMap.of(
                new BuildingGroup("bg_coal_mining"), 32,
                new BuildingGroup("bg_lead_mining"), 24,
                new BuildingGroup("bg_logging"), 17,
                new BuildingGroup("bg_fishing"), 10),
            ImmutableSet.of(
                new DiscoverableResource(
                    new BuildingGroup("bg_rubber"), Optional.empty(), 40, Optional.empty())),
            Optional.of(3052));
    var state2 =
        new State(
            "STATE_WEST_BORNEO",
            542,
            null,
            new Building("building_subsistence_farms", null, null),
            ImmutableSet.of("x029E81", "xFCFD81"),
            ImmutableSet.of("x029E82", "xFCFD82"),
            ImmutableSet.of("x029E83", "xFCFD83"),
            ImmutableSet.of("state_trait_kapuas_river", "state_trait_borneo_rainforest"),
            ImmutableMap.of(
                CITY, "xFFE074",
                PORT, "x4DD36E"),
            22,
            ImmutableSet.of(
                new BuildingGroup("bg_rice_farms"),
                new BuildingGroup("bg_livestock_ranches"),
                new BuildingGroup("bg_coffee_plantations"),
                new BuildingGroup("bg_dye_plantations"),
                new BuildingGroup("bg_tea_plantations"),
                new BuildingGroup("bg_tobacco_plantations"),
                new BuildingGroup("bg_banana_plantations")),
            ImmutableMap.of(
                new BuildingGroup("bg_lead_mining"), 30,
                new BuildingGroup("bg_logging"), 17,
                new BuildingGroup("bg_fishing"), 7),
            ImmutableSet.of(
                new DiscoverableResource(
                    new BuildingGroup("bg_gold_fields"),
                    Optional.of(new BuildingGroup("bg_gold_mining")),
                    6,
                    Optional.of(4)),
                new DiscoverableResource(
                    new BuildingGroup("bg_oil_extraction"),
                    Optional.empty(),
                    40,
                    Optional.empty())),
            Optional.of(3052));

    var strings = StateWriter.serialiseStateRegions(ImmutableSet.of(state1, state2));

    assertThat(String.join("\n", strings) + "\n")
        .isEqualTo(
            """
STATE_MALAYA = {
  id = 540
  subsistence_building = "building_subsistence_rice_paddies"
  provinces = { "x0080B0" "x08673B" }
  city = "x8E3305"
  port = "xC00130"
  farm = "x76546D"
  mine = "x08673B"
  wood = "xCAB9F2"
  arable_land = 35
  arable_resources = { "bg_rice_farms" "bg_livestock_ranches" "bg_coffee_plantations" "bg_tea_plantations" "bg_sugar_plantations" "bg_banana_plantations" }
  capped_resources = {
    bg_coal_mining = 32
    bg_lead_mining = 24
    bg_logging = 17
    bg_fishing = 10
  }
  resource = {
    type = "bg_rubber"
    undiscovered_amount = 40
  }
  naval_exit_id = 3052
}
STATE_WEST_BORNEO = {
  id = 542
  subsistence_building = "building_subsistence_farms"
  provinces = { "x029E81" "xFCFD81" }
  impassable = { "x029E82" "xFCFD82" }
  prime_land = { "x029E83" "xFCFD83" }
  traits = { "state_trait_kapuas_river" "state_trait_borneo_rainforest" }
  city = "xFFE074"
  port = "x4DD36E"
  arable_land = 22
  arable_resources = { "bg_rice_farms" "bg_livestock_ranches" "bg_coffee_plantations" "bg_dye_plantations" "bg_tea_plantations" "bg_tobacco_plantations" "bg_banana_plantations" }
  capped_resources = {
    bg_lead_mining = 30
    bg_logging = 17
    bg_fishing = 7
  }
  resource = {
    type = "bg_gold_fields"
    depleted_type = "bg_gold_mining"
    undiscovered_amount = 6
    discovered_amount = 4
  }
  resource = {
    type = "bg_oil_extraction"
    undiscovered_amount = 40
  }
  naval_exit_id = 3052
}
                """);
  }
}
