package lv.kitn.generator;

import static lv.kitn.generator.BuildingGroup.BG_SUBSISTENCE_AGRICULTURE;
import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.collect.ImmutableList;
import java.io.StringReader;
import org.antlr.v4.runtime.CharStreams;
import org.junit.jupiter.api.Test;

final class BuildingLoaderTest {
  @Test
  void getSubsistenceBuildings() throws Exception {
    var input =
        """
        building_subsistence_fishing_villages = {
            building_group = bg_subsistence_agriculture
            texture = "gfx/interface/icons/building_icons/building_subsistence_farm.dds"

            production_method_groups = {
                pmg_base_building_subsistence_fishing_villages
                pmg_home_workshops_building_subsistence_fishing_villages
                pmg_serfdom_building_subsistence_fishing_villages
                pmg_ownership_building_subsistence
            }

            buildable = no
            expandable = no
            downsizeable = no
            min_raise_to_hire = 0.30
            slaves_role = peasants
        }

        building_subsistence_rice_paddies = {
            building_group = bg_subsistence_agriculture
            texture = "gfx/interface/icons/building_icons/building_subsistence_farm.dds"

            production_method_groups = {
                pmg_base_building_subsistence_rice_paddies
                pmg_home_workshops_building_subsistence_rice_paddies
                pmg_serfdom_building_subsistence_rice_paddies
                pmg_ownership_building_subsistence
            }

            buildable = no
            expandable = no
            downsizeable = no
            min_raise_to_hire = 0.30
            slaves_role = peasants
        }
        """;

    var charStream = CharStreams.fromReader(new StringReader(input));
    var subsistenceBuildings = BuildingLoader.getBuildings(charStream);

    assertThat(subsistenceBuildings)
        .contains(
            new Building(
                "building_subsistence_fishing_villages",
                BG_SUBSISTENCE_AGRICULTURE,
                ImmutableList.of(
                    new ProductionMethodGroup("pmg_base_building_subsistence_fishing_villages"),
                    new ProductionMethodGroup(
                        "pmg_home_workshops_building_subsistence_fishing_villages"),
                    new ProductionMethodGroup("pmg_serfdom_building_subsistence_fishing_villages"),
                    new ProductionMethodGroup("pmg_ownership_building_subsistence"))),
            new Building(
                "building_subsistence_rice_paddies",
                BG_SUBSISTENCE_AGRICULTURE,
                ImmutableList.of(
                    new ProductionMethodGroup("pmg_base_building_subsistence_rice_paddies"),
                    new ProductionMethodGroup(
                        "pmg_home_workshops_building_subsistence_rice_paddies"),
                    new ProductionMethodGroup("pmg_serfdom_building_subsistence_rice_paddies"),
                    new ProductionMethodGroup("pmg_ownership_building_subsistence"))));
  }
}
