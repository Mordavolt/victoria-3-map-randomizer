package lv.kitn.generator;

import static lv.kitn.generator.BuildingGroup.BG_BANANA_PLANTATIONS;
import static lv.kitn.generator.BuildingGroup.BG_COAL_MINING;
import static lv.kitn.generator.BuildingGroup.BG_COFFEE_PLANTATIONS;
import static lv.kitn.generator.BuildingGroup.BG_DYE_PLANTATIONS;
import static lv.kitn.generator.BuildingGroup.BG_FISHING;
import static lv.kitn.generator.BuildingGroup.BG_GOLD_FIELDS;
import static lv.kitn.generator.BuildingGroup.BG_GOLD_MINING;
import static lv.kitn.generator.BuildingGroup.BG_LEAD_MINING;
import static lv.kitn.generator.BuildingGroup.BG_LIVESTOCK_RANCHES;
import static lv.kitn.generator.BuildingGroup.BG_LOGGING;
import static lv.kitn.generator.BuildingGroup.BG_OIL_EXTRACTION;
import static lv.kitn.generator.BuildingGroup.BG_RICE_FARMS;
import static lv.kitn.generator.BuildingGroup.BG_RUBBER;
import static lv.kitn.generator.BuildingGroup.BG_SUGAR_PLANTATIONS;
import static lv.kitn.generator.BuildingGroup.BG_TEA_PLANTATIONS;
import static lv.kitn.generator.BuildingGroup.BG_TOBACCO_PLANTATIONS;
import static lv.kitn.generator.BuildingType.CITY;
import static lv.kitn.generator.BuildingType.FARM;
import static lv.kitn.generator.BuildingType.MINE;
import static lv.kitn.generator.BuildingType.PORT;
import static lv.kitn.generator.BuildingType.WOOD;
import static lv.kitn.generator.Culture.JAVAN;
import static lv.kitn.generator.Culture.MALAY;
import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import java.util.Optional;
import org.junit.jupiter.api.Test;

final class StateWriterTest {

  @Test
  void serializeHistoryStates() throws Exception {
    var state =
        new State(
            "STATE_MALAYA",
            null,
            ImmutableSet.of(MALAY),
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
            ImmutableMap.of(),
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
            ImmutableMap.of(),
            ImmutableSet.of());

    var strings = StateWriter.serializeHistoryStates(ImmutableSet.of(regionState1, regionState2));

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
  void serializeHistoryPops() throws Exception {
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
            ImmutableMap.of(
                new Population(MALAY, Optional.empty(), Optional.empty()), 399_600,
                new Population(MALAY, Optional.empty(), Optional.of("slaves")), 11_000),
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
            ImmutableMap.of(
                new Population(MALAY, Optional.empty(), Optional.empty()), 209_000,
                new Population(JAVAN, Optional.of("animist"), Optional.of("slaves")), 5_400),
            ImmutableSet.of());

    var strings = StateWriter.serializeHistoryPops(ImmutableSet.of(regionState1, regionState2));

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
  void serializeHistoryBuildings() throws Exception {
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
            ImmutableMap.of(),
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
            ImmutableMap.of(),
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
        StateWriter.serializeHistoryBuildings(ImmutableSet.of(regionState1, regionState2));

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
  void serializeStrategicRegions() throws Exception {
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
        StateWriter.serializeStrategicRegions(ImmutableSet.of(strategicRegion1, strategicRegion2));

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
  void serializeStateRegions() throws Exception {
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
                BG_RICE_FARMS,
                BG_LIVESTOCK_RANCHES,
                BG_COFFEE_PLANTATIONS,
                BG_TEA_PLANTATIONS,
                BG_SUGAR_PLANTATIONS,
                BG_BANANA_PLANTATIONS),
            ImmutableMap.of(
                BG_COAL_MINING, 32,
                BG_LEAD_MINING, 24,
                BG_LOGGING, 17,
                BG_FISHING, 10),
            ImmutableSet.of(
                new DiscoverableResource(BG_RUBBER, Optional.empty(), 40, Optional.empty())),
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
                BG_RICE_FARMS,
                BG_LIVESTOCK_RANCHES,
                BG_COFFEE_PLANTATIONS,
                BG_DYE_PLANTATIONS,
                BG_TEA_PLANTATIONS,
                BG_TOBACCO_PLANTATIONS,
                BG_BANANA_PLANTATIONS),
            ImmutableMap.of(
                BG_LEAD_MINING, 30,
                BG_LOGGING, 17,
                BG_FISHING, 7),
            ImmutableSet.of(
                new DiscoverableResource(
                    BG_GOLD_FIELDS, Optional.of(BG_GOLD_MINING), 6, Optional.of(4)),
                new DiscoverableResource(
                    BG_OIL_EXTRACTION, Optional.empty(), 40, Optional.empty())),
            Optional.of(3052));

    var strings = StateWriter.serializeStateRegions(ImmutableSet.of(state1, state2));

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

  @Test
  void serializeCountryLocalizations() throws Exception {
    var stateLocalization1 = new StateLocalization("STATE_MINSK", "Minsk");
    var stateLocalization2 = new StateLocalization("STATE_ALASKA", "Alaska");
    var strings =
        StateWriter.serializeStateLocalizations(
            ImmutableSet.of(stateLocalization1, stateLocalization2));

    assertThat(String.join("\n", strings) + "\n")
        .isEqualTo("""
  STATE_MINSK:0 "Minsk"
  STATE_ALASKA:0 "Alaska"
""");
  }

  @Test
  void serializeStrategicRegionLocalizations() throws Exception {
    var strategicRegionLocalization1 = new StrategicRegionLocalization("region_england", "England");
    var strategicRegionLocalization2 =
        new StrategicRegionLocalization("region_north_sea_coast", "North Sea");
    var strings =
        StateWriter.serializeStrategicRegionLocalizations(
            ImmutableSet.of(strategicRegionLocalization1, strategicRegionLocalization2));

    assertThat(String.join("\n", strings) + "\n")
        .isEqualTo("""
  region_england:0 "England"
  region_north_sea_coast:0 "North Sea"
""");
  }
}
