package lv.kitn.generator;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.collect.ImmutableList;
import java.io.StringReader;
import java.util.Optional;
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
                "bg_subsistence_agriculture",
                ImmutableList.of(
                    new ProductionMethodGroup("pmg_base_building_subsistence_fishing_villages"),
                    new ProductionMethodGroup(
                        "pmg_home_workshops_building_subsistence_fishing_villages"),
                    new ProductionMethodGroup("pmg_serfdom_building_subsistence_fishing_villages"),
                    new ProductionMethodGroup("pmg_ownership_building_subsistence"))),
            new Building(
                "building_subsistence_rice_paddies",
                "bg_subsistence_agriculture",
                ImmutableList.of(
                    new ProductionMethodGroup("pmg_base_building_subsistence_rice_paddies"),
                    new ProductionMethodGroup(
                        "pmg_home_workshops_building_subsistence_rice_paddies"),
                    new ProductionMethodGroup("pmg_serfdom_building_subsistence_rice_paddies"),
                    new ProductionMethodGroup("pmg_ownership_building_subsistence"))));
  }

  @Test
  void getBuildingGroups() throws Exception {
    var input =
        """
            bg_agriculture = {
                category = rural

                land_usage = rural

                lens = agriculture

                economy_of_scale = yes

                can_use_slaves = yes

                urbanization = 5
                infrastructure_usage_per_level = 1

                should_auto_expand = {\s
                    default_auto_expand_rule = yes
                }

                economy_of_scale_ai_factor = 1.5
            }

            bg_rice_farms = {
                parent_group = bg_agriculture

                default_building = building_rice_farm

                cash_reserves_max = 25000
            }

            bg_maize_farms = {
                parent_group = bg_agriculture

                default_building = building_maize_farm

                cash_reserves_max = 25000
            }

            bg_millet_farms = {
                parent_group = bg_agriculture

                default_building = building_millet_farm

                cash_reserves_max = 25000
            }
            """;

    var charStream = CharStreams.fromReader(new StringReader(input));
    var subsistenceBuildings = BuildingLoader.getBuildingGroups(charStream);

    assertThat(subsistenceBuildings)
        .contains(
            new BuildingGroup("bg_agriculture", Optional.empty()),
            new BuildingGroup("bg_rice_farms", Optional.of("bg_agriculture")),
            new BuildingGroup("bg_maize_farms", Optional.of("bg_agriculture")),
            new BuildingGroup("bg_millet_farms", Optional.of("bg_agriculture")));
  }
}
