package building;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.StringReader;
import java.util.List;
import org.antlr.v4.runtime.CharStreams;
import org.junit.jupiter.api.Test;

final class BuildingLoaderTest {

  @Test
  void getSubsistenceBuildings() throws Exception {
    // Given
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

    // When
    var charStream = CharStreams.fromReader(new StringReader(input));
    var subsistenceBuildings = BuildingLoader.getBuildings(charStream);

    // Then
    assertThat(subsistenceBuildings)
        .contains(
            new Building(
                "building_subsistence_fishing_villages",
                new BuildingGroup("bg_subsistence_agriculture"),
                List.of(
                    new ProductionMethodGroup("pmg_base_building_subsistence_fishing_villages"),
                    new ProductionMethodGroup(
                        "pmg_home_workshops_building_subsistence_fishing_villages"),
                    new ProductionMethodGroup("pmg_serfdom_building_subsistence_fishing_villages"),
                    new ProductionMethodGroup("pmg_ownership_building_subsistence"))),
            new Building(
                "building_subsistence_rice_paddies",
                new BuildingGroup("bg_subsistence_agriculture"),
                List.of(
                    new ProductionMethodGroup("pmg_base_building_subsistence_rice_paddies"),
                    new ProductionMethodGroup(
                        "pmg_home_workshops_building_subsistence_rice_paddies"),
                    new ProductionMethodGroup("pmg_serfdom_building_subsistence_rice_paddies"),
                    new ProductionMethodGroup("pmg_ownership_building_subsistence"))));
  }

  @Test
  void getBuildingGroups() throws Exception { // Given
    var input =
        """
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

    // When
    var charStream = CharStreams.fromReader(new StringReader(input));
    var subsistenceBuildings = BuildingLoader.getBuildingGroups(charStream);

    // Then
    assertThat(subsistenceBuildings)
        .contains(
            new BuildingGroup("bg_rice_farms"),
            new BuildingGroup("bg_maize_farms"),
            new BuildingGroup("bg_millet_farms"));
  }
}
