package lv.kitn.generator;

import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;
import static com.google.common.collect.ImmutableMap.toImmutableMap;
import static com.google.common.collect.ImmutableSet.toImmutableSet;
import static com.google.common.collect.ImmutableSetMultimap.flatteningToImmutableSetMultimap;
import static com.google.common.collect.ImmutableSetMultimap.toImmutableSetMultimap;
import static com.google.common.collect.Sets.toImmutableEnumSet;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Map.Entry.comparingByValue;
import static java.util.function.Function.identity;
import static lv.kitn.generator.BuildingGroup.BG_BANANA_PLANTATIONS;
import static lv.kitn.generator.BuildingGroup.BG_COAL_MINING;
import static lv.kitn.generator.BuildingGroup.BG_COFFEE_PLANTATIONS;
import static lv.kitn.generator.BuildingGroup.BG_COTTON_PLANTATIONS;
import static lv.kitn.generator.BuildingGroup.BG_DYE_PLANTATIONS;
import static lv.kitn.generator.BuildingGroup.BG_FISHING;
import static lv.kitn.generator.BuildingGroup.BG_GOLD_FIELDS;
import static lv.kitn.generator.BuildingGroup.BG_GOLD_MINING;
import static lv.kitn.generator.BuildingGroup.BG_IRON_MINING;
import static lv.kitn.generator.BuildingGroup.BG_LEAD_MINING;
import static lv.kitn.generator.BuildingGroup.BG_LIVESTOCK_RANCHES;
import static lv.kitn.generator.BuildingGroup.BG_LOGGING;
import static lv.kitn.generator.BuildingGroup.BG_MAIZE_FARMS;
import static lv.kitn.generator.BuildingGroup.BG_MILLET_FARMS;
import static lv.kitn.generator.BuildingGroup.BG_OIL_EXTRACTION;
import static lv.kitn.generator.BuildingGroup.BG_OPIUM_PLANTATIONS;
import static lv.kitn.generator.BuildingGroup.BG_RICE_FARMS;
import static lv.kitn.generator.BuildingGroup.BG_RUBBER;
import static lv.kitn.generator.BuildingGroup.BG_RYE_FARMS;
import static lv.kitn.generator.BuildingGroup.BG_SILK_PLANTATIONS;
import static lv.kitn.generator.BuildingGroup.BG_SUBSISTENCE_AGRICULTURE;
import static lv.kitn.generator.BuildingGroup.BG_SUGAR_PLANTATIONS;
import static lv.kitn.generator.BuildingGroup.BG_SULFUR_MINING;
import static lv.kitn.generator.BuildingGroup.BG_TEA_PLANTATIONS;
import static lv.kitn.generator.BuildingGroup.BG_TOBACCO_PLANTATIONS;
import static lv.kitn.generator.BuildingGroup.BG_VINEYARD_PLANTATIONS;
import static lv.kitn.generator.BuildingGroup.BG_WHALING;
import static lv.kitn.generator.BuildingGroup.BG_WHEAT_FARMS;
import static lv.kitn.generator.MapAdjacencyService.getGroups;
import static lv.kitn.generator.NameGenerator.generateAdjective;
import static lv.kitn.generator.NameGenerator.generateName;
import static lv.kitn.generator.Politics.TRADITIONAL;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Maps;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class RunGenerator {
  private static final Logger LOG = LoggerFactory.getLogger(RunGenerator.class);

  public static void main(String... args) {
    var random = new Random();
    var properties = getProperties();
    var input = properties.input();
    var provinces =
        ProvinceLoader.loadProvinces(input.gameInstallationPath() + input.provinceTerrains());

    var adjacencyMatrix =
        MapAdjacencyService.findAdjacencyMatrix(
            input.gameInstallationPath() + input.provinceImage());

    var landAdjacencyMatrix =
        adjacencyMatrix.entries().stream()
            .filter(
                entry ->
                    provinces.get(entry.getKey()).isLand()
                        && provinces.get(entry.getValue()).isLand())
            .collect(toImmutableSetMultimap(Map.Entry::getKey, Map.Entry::getValue));

    var additionalAdjacency =
        MapAdjacencyService.loadAdditionalAdjacencyMatrix(
            input.gameInstallationPath() + input.adjacencies());

    var landAdditionalAdjacency =
        additionalAdjacency.entries().stream()
            .filter(
                entry ->
                    provinces.get(entry.getKey()).isLand()
                        && provinces.get(entry.getValue()).isLand())
            .collect(toImmutableSetMultimap(Map.Entry::getKey, Map.Entry::getValue));

    var fullAdjacency =
        ImmutableSetMultimap.<String, String>builder()
            .putAll(landAdjacencyMatrix)
            .putAll(landAdditionalAdjacency)
            .build();

    var buildings =
        input.buildings().stream()
            .map(path -> input.gameInstallationPath() + path)
            .map(BuildingLoader::loadBuildings)
            .flatMap(Collection::stream)
            .collect(toImmutableSet());

    var cultures =
        input.cultures().stream()
            .map(path -> input.gameInstallationPath() + path)
            .map(CultureLoader::loadCultures)
            .flatMap(Collection::stream)
            .collect(toImmutableSet());

    var seas =
        Maps.uniqueIndex(
            SeaLoader.loadSeas(input.gameInstallationPath() + input.seas()), Sea::province);

    var provincePrefabs =
        Maps.uniqueIndex(
            generateProvincePrefabs(provinces, cultures, random, adjacencyMatrix),
            ProvincePrefab::id);

    var countries = generateCountries(provincePrefabs, random, fullAdjacency);
    var states =
        generateStates(buildings, provincePrefabs, random, seas, adjacencyMatrix, fullAdjacency);
    var regionStates = generateRegionStates(states, countries, provincePrefabs);
    var stateAdjacency = calculateStateAdjacency(states, fullAdjacency);
    var strategicRegions = generateStrategicRegions(states, stateAdjacency, random);
    var countryLocalizations = generateCountryLocalizations(countries, random);
    var stateLocalizations = generateStateLocalizations(states, random);
    var strategicRegionLocalizations =
        generateStrategicRegionLocalizations(strategicRegions, random);

    var output = properties.output();

    writeMetadata(output.modPath() + output.metadata(), getMetadata());
    writeEmptyFilesForOverride(output.modPath(), output.emptyFilesToCreate());
    StateWriter.writeHistoryStates(regionStates, output.modPath() + output.states());
    StateWriter.writeHistoryPops(regionStates, output.modPath() + output.pops());
    StateWriter.writeHistoryBuildings(regionStates, output.modPath() + output.buildings());
    StateWriter.writeStrategicRegions(
        strategicRegions, output.modPath() + output.strategicRegions());
    StateWriter.writeStrategicRegionLocalizations(
        strategicRegionLocalizations, output.modPath() + output.strategicRegionLocalization());
    StateWriter.writeStateRegions(states, output.modPath() + output.stateRegions());
    StateWriter.writeStateLocalizations(
        stateLocalizations, output.modPath() + output.stateLocalization());
    CountryWriter.writeHistoryCountries(countries, output.modPath() + output.countries());
    CountryWriter.writeCountryDefinitions(
        countries, output.modPath() + output.countryDefinitions());
    CountryWriter.writeCountryLocalizations(
        countryLocalizations, output.modPath() + output.countryLocalization());

    LOG.debug("Generation done");
  }

  private static ImmutableSet<ProvincePrefab> generateProvincePrefabs(
      ImmutableMap<String, Terrain> provinces,
      ImmutableSet<Culture> cultures,
      Random random,
      ImmutableSetMultimap<String, String> adjacencyMatrix) {
    var landProvinces = Maps.filterValues(provinces, Terrain::isLand).keySet();
    var coastalProvinces =
        adjacencyMatrix.entries().stream()
            .filter(entry -> !provinces.get(entry.getValue()).isLand())
            .map(Map.Entry::getKey)
            .filter(landProvinces::contains)
            .collect(toImmutableSet());
    var cultureList = cultures.asList();
    return provinces.entrySet().stream()
        .map(
            entry ->
                new ProvincePrefab(
                    entry.getKey(),
                    entry.getValue(),
                    coastalProvinces.contains(entry.getKey()),
                    ImmutableMap.of(
                        new Population(
                            cultureList.get(random.nextInt(cultureList.size())),
                            Optional.empty(),
                            random.nextInt(10) > 6 ? Optional.of("slaves") : Optional.empty()),
                        random.nextInt(10_000) + 100)))
        .collect(toImmutableSet());
  }

  private static ImmutableSetMultimap<String, String> calculateStateAdjacency(
      ImmutableSet<State> states, ImmutableSetMultimap<String, String> fullAdjacency) {
    LOG.debug("Calculating state adjacency");

    var provinceToState =
        states.stream()
            .flatMap(
                state ->
                    state.provinces().stream()
                        .map(province -> Map.entry(province, state.variableName())))
            .collect(toImmutableMap(Map.Entry::getKey, Map.Entry::getValue));
    return fullAdjacency.entries().stream()
        .map(e -> Map.entry(provinceToState.get(e.getKey()), provinceToState.get(e.getValue())))
        .filter(e -> !e.getKey().equals(e.getValue()))
        .collect(toImmutableSetMultimap(Map.Entry::getKey, Map.Entry::getValue));
  }

  private static ImmutableSet<Country> generateCountries(
      ImmutableMap<String, ProvincePrefab> provincePrefabs,
      Random random,
      ImmutableSetMultimap<String, String> fullAdjacency) {
    LOG.debug("Generating countries");
    var groupedProvinces = getGroups(fullAdjacency, 200, random, 0.9);
    var result = ImmutableSet.<Country>builder();
    int i = 0;
    for (var provinces : groupedProvinces) {
      var topCulture =
          provinces.stream()
              .map(provincePrefabs::get)
              .map(ProvincePrefab::populations)
              .map(ImmutableMap::entrySet)
              .flatMap(Collection::stream)
              .collect(toImmutableMap(p -> p.getKey().culture(), Map.Entry::getValue, Integer::sum))
              .entrySet()
              .stream()
              .max(comparingByValue())
              .map(Map.Entry::getKey)
              .orElseThrow();
      result.add(
          new Country(
              "COUNTRY_" + i++,
              ImmutableSet.of(),
              ImmutableSet.of(),
              ImmutableSet.of(),
              TRADITIONAL,
              4,
              Optional.empty(),
              ImmutableMap.of(),
              2,
              ImmutableMap.of(),
              ImmutableSet.of(),
              ImmutableMap.of(),
              ImmutableMap.of(),
              ImmutableSet.of(),
              ImmutableMap.of(),
              new Color(random.nextDouble(), random.nextDouble(), random.nextDouble()),
              CountryType.values()[random.nextInt(CountryType.values().length)],
              CountryTier.values()[random.nextInt(CountryTier.values().length)],
              ImmutableSet.of(topCulture),
              provinces.iterator().next(),
              provinces));
    }
    return result.build();
  }

  private static ImmutableSet<State> generateStates(
      ImmutableSet<Building> buildings,
      ImmutableMap<String, ProvincePrefab> provincePrefabs,
      Random random,
      ImmutableMap<String, Sea> seas,
      ImmutableSetMultimap<String, String> adjacencyMatrix,
      ImmutableSetMultimap<String, String> fullAdjacency) {
    LOG.debug("Generating states");
    var groupedProvinces = getGroups(fullAdjacency, 100, random, 0.5);
    var result = ImmutableSet.<State>builder();
    int i = 0;
    for (var provinces : groupedProvinces) {
      var terrainToProvincePrefabs =
          provinces.stream()
              .map(provincePrefabs::get)
              .collect(toImmutableSetMultimap(ProvincePrefab::terrain, identity()));
      var coastalToProvincePrefabs =
          provinces.stream()
              .map(provincePrefabs::get)
              .collect(toImmutableSetMultimap(ProvincePrefab::coastal, identity()));
      result.add(
          new State(
              "STATE_" + i,
              i,
              ImmutableList.of(new Culture("malay")),
              buildings.stream()
                  .filter(b -> b.buildingGroup() == BG_SUBSISTENCE_AGRICULTURE)
                  .findAny()
                  .orElseThrow(),
              provinces,
              ImmutableSet.of(),
              ImmutableSet.of(),
              ImmutableSet.of(),
              ImmutableMap.of(),
              calculateArableLand(terrainToProvincePrefabs),
              generateArableResources(terrainToProvincePrefabs, random),
              generateCappedResources(coastalToProvincePrefabs, random),
              generateDiscoverableResources(terrainToProvincePrefabs, random),
              provinces.stream()
                  .map(adjacencyMatrix::get)
                  .flatMap(Collection::stream)
                  .map(seas::get)
                  .filter(Objects::nonNull)
                  .map(Sea::id)
                  .findAny()));
      i++;
    }
    return result.build();
  }

  private static int calculateArableLand(
      ImmutableSetMultimap<Terrain, ProvincePrefab> terrainToProvincePrefabs) {
    return terrainToProvincePrefabs.asMap().entrySet().stream()
        .mapToInt(
            entry ->
                (int)
                        (entry.getValue().size()
                            * switch (entry.getKey()) {
                              case LAKES, OCEAN -> 0;
                              case MOUNTAIN, TUNDRA, SNOW -> 0.2;
                              case HILLS -> 0.4;
                              case DESERT, FOREST, JUNGLE -> 0.6;
                              case SAVANNA, WETLAND -> 0.8;
                              case PLAINS -> 1;
                            })
                    + 1)
        .sum();
  }

  private static ImmutableSet<BuildingGroup> generateArableResources(
      ImmutableSetMultimap<Terrain, ProvincePrefab> terrainToProvincePrefabs, Random random) {
    var totalProvinces = terrainToProvincePrefabs.size();
    return terrainToProvincePrefabs.asMap().entrySet().stream()
        .flatMap(
            entry ->
                getArableResourceChanceForTerrain(entry.getKey()).entrySet().stream()
                    .filter(
                        e ->
                            rollBuldingChanceScaledToTerrainChanceInState(
                                random, entry.getValue().size(), totalProvinces, e.getValue())))
        .map(Map.Entry::getKey)
        .collect(toImmutableEnumSet());
  }

  private static boolean rollBuldingChanceScaledToTerrainChanceInState(
      Random random, double provincesOfThisTerrain, int totalProvinces, Double chanceOfBuilding) {
    return random.nextDouble() < chanceOfBuilding * (provincesOfThisTerrain / totalProvinces);
  }

  private static ImmutableMap<BuildingGroup, Integer> generateCappedResources(
      ImmutableSetMultimap<Boolean, ProvincePrefab> coastalToProvincePrefabs, Random random) {
    var coastalProvinces = coastalToProvincePrefabs.get(true);
    if (coastalProvinces.isEmpty()) return ImmutableMap.of();
    var waterResources =
        ImmutableMap.<BuildingGroup, Integer>builder().put(BG_FISHING, coastalProvinces.size());
    if (random.nextDouble() < 0.3) {
      waterResources.put(BG_WHALING, random.nextInt(coastalProvinces.size()) + 1);
    }
    return waterResources.build();
  }

  private static ImmutableSet<DiscoverableResource> generateDiscoverableResources(
      ImmutableSetMultimap<Terrain, ProvincePrefab> terrainToProvincePrefabs, Random random) {
    var totalProvinces = terrainToProvincePrefabs.size();
    var discoverableResourcesWithoutGold =
        terrainToProvincePrefabs.asMap().entrySet().stream()
            .flatMap(
                entry ->
                    getDiscoverableNaturalResourceChanceForTerrain(entry.getKey())
                        .entrySet()
                        .stream()
                        .filter(
                            e ->
                                rollBuldingChanceScaledToTerrainChanceInState(
                                    random, entry.getValue().size(), totalProvinces, e.getValue())))
            .map(Map.Entry::getKey)
            .distinct()
            .map(
                buildingGroup -> {
                  var resourceCount = random.nextInt(totalProvinces) + 1;
                  var discovered =
                      switch (buildingGroup) {
                        case BG_LOGGING -> resourceCount;
                        case BG_RUBBER, BG_OIL_EXTRACTION -> 0;
                        default -> random.nextInt(resourceCount);
                      };
                  return new DiscoverableResource(
                      buildingGroup,
                      Optional.empty(),
                      resourceCount - discovered,
                      Optional.of(discovered));
                })
            .collect(toImmutableSet());
    var discoverableResources =
        ImmutableSet.<DiscoverableResource>builder().addAll(discoverableResourcesWithoutGold);
    if (random.nextDouble() < 0.03) {
      var goldMineCount = random.nextInt(20) + 1;
      var discovered = random.nextInt(goldMineCount);
      discoverableResources.add(
          new DiscoverableResource(
              BG_GOLD_FIELDS,
              Optional.of(BG_GOLD_MINING),
              goldMineCount - discovered,
              Optional.of(discovered)));
    }
    return discoverableResources.build();
  }

  private static ImmutableMap<BuildingGroup, Double> getArableResourceChanceForTerrain(
      Terrain terrain) {
    return switch (terrain) {
      case SNOW, DESERT, TUNDRA ->
          ImmutableMap.<BuildingGroup, Double>builder()
              //  .put(BG_RYE_FARMS, 0.0)
              //  .put(BG_WHEAT_FARMS, 0.0)
              //  .put(BG_RICE_FARMS, 0.0)
              //  .put(BG_MAIZE_FARMS, 0.0)
              //  .put(BG_MILLET_FARMS, 0.0)
              //  .put(BG_VINEYARD_PLANTATIONS, 0.0)
              .put(BG_LIVESTOCK_RANCHES, 1.0)
              //  .put(BG_COFFEE_PLANTATIONS, 0.0)
              //  .put(BG_COTTON_PLANTATIONS, 0.0)
              //  .put(BG_SILK_PLANTATIONS, 0.0)
              //  .put(BG_DYE_PLANTATIONS, 0.0)
              //  .put(BG_OPIUM_PLANTATIONS, 0.0)
              //  .put(BG_TEA_PLANTATIONS, 0.0)
              //  .put(BG_TOBACCO_PLANTATIONS, 0.0)
              //  .put(BG_SUGAR_PLANTATIONS, 0.0)
              //  .put(BG_BANANA_PLANTATIONS, 0.0)
              .build();
      case FOREST ->
          ImmutableMap.<BuildingGroup, Double>builder()
              //  .put(BG_RYE_FARMS, 0.0)
              //  .put(BG_WHEAT_FARMS, 0.0)
              //  .put(BG_RICE_FARMS, 0.0)
              //  .put(BG_MAIZE_FARMS, 0.0)
              //  .put(BG_MILLET_FARMS, 0.0)
              //  .put(BG_VINEYARD_PLANTATIONS, 0.0)
              .put(BG_LIVESTOCK_RANCHES, 0.6)
              .put(BG_COFFEE_PLANTATIONS, 0.2)
              .put(BG_COTTON_PLANTATIONS, 0.2)
              //  .put(BG_SILK_PLANTATIONS, 0.0)
              //  .put(BG_DYE_PLANTATIONS, 0.0)
              .put(BG_OPIUM_PLANTATIONS, 0.2)
              .put(BG_TEA_PLANTATIONS, 0.2)
              .put(BG_TOBACCO_PLANTATIONS, 0.2)
              .put(BG_SUGAR_PLANTATIONS, 0.2)
              .put(BG_BANANA_PLANTATIONS, 0.2)
              .build();
      case HILLS ->
          ImmutableMap.<BuildingGroup, Double>builder()
              .put(BG_RYE_FARMS, 0.2)
              .put(BG_WHEAT_FARMS, 0.2)
              //  .put(BG_RICE_FARMS, 0.0)
              .put(BG_MAIZE_FARMS, 0.2)
              .put(BG_MILLET_FARMS, 0.2)
              .put(BG_VINEYARD_PLANTATIONS, 0.2)
              .put(BG_LIVESTOCK_RANCHES, 0.2)
              .put(BG_COFFEE_PLANTATIONS, 0.2)
              .put(BG_COTTON_PLANTATIONS, 0.2)
              .put(BG_SILK_PLANTATIONS, 0.2)
              .put(BG_DYE_PLANTATIONS, 0.2)
              .put(BG_OPIUM_PLANTATIONS, 0.2)
              .put(BG_TEA_PLANTATIONS, 0.2)
              .put(BG_TOBACCO_PLANTATIONS, 0.2)
              //  .put(BG_SUGAR_PLANTATIONS, 0.0)
              //  .put(BG_BANANA_PLANTATIONS, 0.0)
              .build();
      case JUNGLE ->
          ImmutableMap.<BuildingGroup, Double>builder()
              //  .put(BG_RYE_FARMS, 0.0)
              //  .put(BG_WHEAT_FARMS, 0.0)
              //  .put(BG_RICE_FARMS, 0.0)
              //  .put(BG_MAIZE_FARMS, 0.0)
              //  .put(BG_MILLET_FARMS, 0.0)
              //  .put(BG_VINEYARD_PLANTATIONS, 0.0)
              .put(BG_LIVESTOCK_RANCHES, 0.7)
              .put(BG_COFFEE_PLANTATIONS, 0.2)
              .put(BG_COTTON_PLANTATIONS, 0.2)
              //  .put(BG_SILK_PLANTATIONS, 0.0)
              .put(BG_DYE_PLANTATIONS, 0.2)
              .put(BG_OPIUM_PLANTATIONS, 0.2)
              .put(BG_TEA_PLANTATIONS, 0.2)
              .put(BG_TOBACCO_PLANTATIONS, 0.2)
              .put(BG_SUGAR_PLANTATIONS, 0.2)
              .put(BG_BANANA_PLANTATIONS, 0.2)
              .build();
      case LAKES, OCEAN ->
          throw new IllegalStateException("Can't produce arable resources for water terrain");
      case MOUNTAIN ->
          ImmutableMap.<BuildingGroup, Double>builder()
              .put(BG_RYE_FARMS, 0.4)
              .put(BG_WHEAT_FARMS, 0.4)
              //  .put(BG_RICE_FARMS, 0.0)
              //  .put(BG_MAIZE_FARMS, 0.0)
              //  .put(BG_MILLET_FARMS, 0.0)
              //  .put(BG_VINEYARD_PLANTATIONS, 0.0)
              .put(BG_LIVESTOCK_RANCHES, 0.4)
              .put(BG_COFFEE_PLANTATIONS, 0.2)
              .put(BG_COTTON_PLANTATIONS, 0.2)
              //  .put(BG_SILK_PLANTATIONS, 0.0)
              //  .put(BG_DYE_PLANTATIONS, 0.0)
              //  .put(BG_OPIUM_PLANTATIONS, 0.0)
              //  .put(BG_TEA_PLANTATIONS, 0.0)
              //  .put(BG_TOBACCO_PLANTATIONS, 0.0)
              //  .put(BG_SUGAR_PLANTATIONS, 0.0)
              //  .put(BG_BANANA_PLANTATIONS, 0.0)
              .build();
      case PLAINS ->
          ImmutableMap.<BuildingGroup, Double>builder()
              .put(BG_RYE_FARMS, 0.2)
              .put(BG_WHEAT_FARMS, 0.2)
              .put(BG_RICE_FARMS, 0.2)
              .put(BG_MAIZE_FARMS, 0.2)
              .put(BG_MILLET_FARMS, 0.2)
              .put(BG_VINEYARD_PLANTATIONS, 0.2)
              .put(BG_LIVESTOCK_RANCHES, 0.2)
              .put(BG_COFFEE_PLANTATIONS, 0.2)
              .put(BG_COTTON_PLANTATIONS, 0.2)
              .put(BG_SILK_PLANTATIONS, 0.2)
              .put(BG_DYE_PLANTATIONS, 0.2)
              .put(BG_OPIUM_PLANTATIONS, 0.2)
              .put(BG_TEA_PLANTATIONS, 0.2)
              .put(BG_TOBACCO_PLANTATIONS, 0.2)
              .put(BG_SUGAR_PLANTATIONS, 0.2)
              .put(BG_BANANA_PLANTATIONS, 0.2)
              .build();
      case SAVANNA ->
          ImmutableMap.<BuildingGroup, Double>builder()
              .put(BG_RYE_FARMS, 0.2)
              .put(BG_WHEAT_FARMS, 0.2)
              //  .put(BG_RICE_FARMS, 0.0)
              .put(BG_MAIZE_FARMS, 0.2)
              .put(BG_MILLET_FARMS, 0.2)
              //  .put(BG_VINEYARD_PLANTATIONS, 0.0)
              .put(BG_LIVESTOCK_RANCHES, 0.6)
              .put(BG_COFFEE_PLANTATIONS, 0.2)
              .put(BG_COTTON_PLANTATIONS, 0.2)
              //  .put(BG_SILK_PLANTATIONS, 0.0)
              .put(BG_DYE_PLANTATIONS, 0.2)
              .put(BG_OPIUM_PLANTATIONS, 0.2)
              //  .put(BG_TEA_PLANTATIONS, 0.0)
              .put(BG_TOBACCO_PLANTATIONS, 0.2)
              //  .put(BG_SUGAR_PLANTATIONS, 0.0)
              //  .put(BG_BANANA_PLANTATIONS, 0.0)
              .build();
      case WETLAND ->
          ImmutableMap.<BuildingGroup, Double>builder()
              //  .put(BG_RYE_FARMS, 0.0)
              //  .put(BG_WHEAT_FARMS, 0.0)
              .put(BG_RICE_FARMS, 0.4)
              //  .put(BG_MAIZE_FARMS, 0.0)
              //  .put(BG_MILLET_FARMS, 0.0)
              //  .put(BG_VINEYARD_PLANTATIONS, 0.0)
              //  .put(BG_LIVESTOCK_RANCHES, 0.0)
              .put(BG_COFFEE_PLANTATIONS, 0.2)
              .put(BG_COTTON_PLANTATIONS, 0.2)
              //  .put(BG_SILK_PLANTATIONS, 0.0)
              .put(BG_DYE_PLANTATIONS, 0.2)
              //  .put(BG_OPIUM_PLANTATIONS, 0.0)
              //  .put(BG_TEA_PLANTATIONS, 0.0)
              //  .put(BG_TOBACCO_PLANTATIONS, 0.0)
              .put(BG_SUGAR_PLANTATIONS, 0.2)
              .put(BG_BANANA_PLANTATIONS, 0.2)
              .build();
    };
  }

  private static ImmutableMap<BuildingGroup, Double> getDiscoverableNaturalResourceChanceForTerrain(
      Terrain terrain) {
    return switch (terrain) {
      case DESERT ->
          ImmutableMap.of(
              BG_LOGGING,
              0.0,
              BG_COAL_MINING,
              0.0,
              BG_IRON_MINING,
              0.0,
              BG_LEAD_MINING,
              0.0,
              BG_SULFUR_MINING,
              0.2,
              BG_RUBBER,
              0.0,
              BG_OIL_EXTRACTION,
              0.3);
      case FOREST ->
          ImmutableMap.of(
              BG_LOGGING,
              1.0,
              BG_COAL_MINING,
              0.2,
              BG_IRON_MINING,
              0.2,
              BG_LEAD_MINING,
              0.1,
              BG_SULFUR_MINING,
              0.1,
              BG_RUBBER,
              0.1,
              BG_OIL_EXTRACTION,
              0.1);
      case HILLS ->
          ImmutableMap.of(
              BG_LOGGING, 0.6,
              BG_COAL_MINING, 0.2,
              BG_IRON_MINING, 0.2,
              BG_LEAD_MINING, 0.1,
              BG_SULFUR_MINING, 0.1,
              BG_RUBBER, 0.1,
              BG_OIL_EXTRACTION, 0.1);
      case PLAINS ->
          ImmutableMap.of(
              BG_LOGGING, 0.0,
              BG_COAL_MINING, 0.2,
              BG_IRON_MINING, 0.2,
              BG_LEAD_MINING, 0.1,
              BG_SULFUR_MINING, 0.1,
              BG_RUBBER, 0.1,
              BG_OIL_EXTRACTION, 0.1);
      case JUNGLE ->
          ImmutableMap.of(
              BG_LOGGING,
              1.0,
              BG_COAL_MINING,
              0.2,
              BG_IRON_MINING,
              0.2,
              BG_LEAD_MINING,
              0.2,
              BG_SULFUR_MINING,
              0.1,
              BG_RUBBER,
              0.2,
              BG_OIL_EXTRACTION,
              0.1);
      case LAKES, OCEAN -> {
        throw new IllegalStateException("Can't produce natural resources for water terrain");
      }
      case MOUNTAIN ->
          ImmutableMap.of(
              BG_LOGGING,
              0.4,
              BG_COAL_MINING,
              0.4,
              BG_IRON_MINING,
              0.4,
              BG_LEAD_MINING,
              0.3,
              BG_SULFUR_MINING,
              0.3,
              BG_RUBBER,
              0.0,
              BG_OIL_EXTRACTION,
              0.0);
      case SAVANNA, WETLAND ->
          ImmutableMap.of(
              BG_LOGGING,
              0.2,
              BG_COAL_MINING,
              0.2,
              BG_IRON_MINING,
              0.2,
              BG_LEAD_MINING,
              0.2,
              BG_SULFUR_MINING,
              0.1,
              BG_RUBBER,
              0.0,
              BG_OIL_EXTRACTION,
              0.1);
      case TUNDRA ->
          ImmutableMap.of(
              BG_LOGGING,
              0.0,
              BG_COAL_MINING,
              0.2,
              BG_IRON_MINING,
              0.2,
              BG_LEAD_MINING,
              0.2,
              BG_SULFUR_MINING,
              0.1,
              BG_RUBBER,
              0.0,
              BG_OIL_EXTRACTION,
              0.0);
      case SNOW ->
          ImmutableMap.of(
              BG_LOGGING,
              0.3,
              BG_COAL_MINING,
              0.2,
              BG_IRON_MINING,
              0.2,
              BG_LEAD_MINING,
              0.2,
              BG_SULFUR_MINING,
              0.1,
              BG_RUBBER,
              0.0,
              BG_OIL_EXTRACTION,
              0.0);
    };
  }

  private static ImmutableSet<RegionState> generateRegionStates(
      ImmutableSet<State> states,
      ImmutableSet<Country> countries,
      ImmutableMap<String, ProvincePrefab> provincePrefabs) {
    LOG.debug("Generating region states");
    var provinceToState =
        states.stream()
            .<Map.Entry<String, State>>mapMulti(
                (state, consumer) ->
                    state.provinces().stream()
                        .forEach(province -> consumer.accept(Map.entry(province, state))))
            .collect(toImmutableMap(Map.Entry::getKey, Map.Entry::getValue));
    var result = ImmutableSet.<RegionState>builder();
    ImmutableSetMultimap<State, Country> stateToCountriesPresentInState =
        countries.stream()
            .collect(
                flatteningToImmutableSetMultimap(
                    identity(), country -> country.provinces().stream().map(provinceToState::get)))
            .inverse();

    for (var state : states) {
      for (Country country : stateToCountriesPresentInState.get(state)) {
        var countryProvincesInThisState =
            state.provinces().stream()
                .filter(province -> country.provinces().contains(province))
                .collect(toImmutableSet());

        var populations =
            countryProvincesInThisState.stream()
                .map(provincePrefabs::get)
                .map(ProvincePrefab::populations)
                .map(ImmutableMap::entrySet)
                .flatMap(Collection::stream)
                .collect(toImmutableMap(Map.Entry::getKey, Map.Entry::getValue, Integer::sum));
        result.add(
            new RegionState(
                state, country, countryProvincesInThisState, populations, ImmutableSet.of()));
      }
    }
    return result.build();
  }

  private static ImmutableSet<StrategicRegion> generateStrategicRegions(
      ImmutableSet<State> states,
      ImmutableSetMultimap<String, String> stateAdjacency,
      Random random) {
    LOG.debug("Generating strategic regions");
    var stateMap = Maps.uniqueIndex(states, State::variableName);
    var stateGroups =
        getGroups(stateAdjacency, 10, random, 0.5).stream()
            .map(stateNames -> stateNames.stream().map(stateMap::get).collect(toImmutableSet()))
            .collect(toImmutableSet());
    var result = ImmutableSet.<StrategicRegion>builder();
    var i = 0;
    for (ImmutableSet<State> group : stateGroups) {
      result.add(
          new StrategicRegion(
              "strategic_" + i++,
              "asian",
              group.iterator().next().provinces().iterator().next(),
              new Color(random.nextDouble(), random.nextDouble(), random.nextDouble()),
              group));
    }
    return result.build();
  }

  private static ImmutableSet<CountryLocalization> generateCountryLocalizations(
      ImmutableSet<Country> countries, Random random) {
    return countries.stream()
        .map(
            country -> {
              var countryName = generateName(random);
              return new CountryLocalization(
                  country.id(), countryName, generateAdjective(countryName));
            })
        .collect(toImmutableSet());
  }

  private static ImmutableSet<StateLocalization> generateStateLocalizations(
      ImmutableSet<State> states, Random random) {
    return states.stream()
        .map(state -> new StateLocalization(state.variableName(), generateName(random)))
        .collect(toImmutableSet());
  }

  private static ImmutableSet<StrategicRegionLocalization> generateStrategicRegionLocalizations(
      ImmutableSet<StrategicRegion> strategicRegions, Random random) {
    return strategicRegions.stream()
        .map(
            strategicRegion ->
                new StrategicRegionLocalization(strategicRegion.name(), generateName(random)))
        .collect(toImmutableSet());
  }

  private static void writeEmptyFilesForOverride(String modPath, List<String> emptyFilesToCreate) {
    for (String relativePath : emptyFilesToCreate) {
      var filePath = modPath + relativePath;
      LOG.debug("Writing an empty file to {}", filePath);
      try {
        new File(filePath).getParentFile().mkdirs();
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, UTF_8));
        writer.write('\ufeff');
        writer.newLine();
        writer.close();
      } catch (Exception e) {
        throw new RuntimeException("Could not write an empty file to " + filePath, e);
      }
    }
  }

  private static void writeMetadata(String metadataPath, Metadata metadata) {
    LOG.debug("Writing metadata to {}", metadataPath);
    try {
      new File(metadataPath).getParentFile().mkdirs();
      BufferedWriter writer = new BufferedWriter(new FileWriter(metadataPath, UTF_8));

      ObjectMapper objectMapper = new ObjectMapper().enable(INDENT_OUTPUT);
      objectMapper.writeValue(writer, metadata);
    } catch (Exception e) {
      throw new RuntimeException("Could not write metadata to " + metadataPath, e);
    }
  }

  private static FileProperties getProperties() {
    var gamePath = "C:/Program Files (x86)/Steam/steamapps/common/Victoria 3/game";
    var modPath = "C:/Users/Admin/Documents/Paradox Interactive/Victoria 3/mod/random_world";
    return new FileProperties(
        new FileProperties.Input(
            gamePath,
            "/map_data/provinces.png",
            "/map_data/province_terrains.txt",
            "/map_data/adjacencies.csv",
            List.of(
                "/common/buildings/01_industry.txt",
                "/common/buildings/02_agro.txt",
                "/common/buildings/03_mines.txt",
                "/common/buildings/04_plantations.txt",
                "/common/buildings/05_military.txt",
                "/common/buildings/06_urban_center.txt",
                "/common/buildings/07_government.txt",
                "/common/buildings/08_monuments.txt",
                "/common/buildings/09_misc_resource.txt",
                "/common/buildings/10_canals.txt",
                "/common/buildings/11_private_infrastructure.txt",
                "/common/buildings/12_subsistence.txt",
                "/common/buildings/13_construction.txt"),
            "/common/building_groups/00_building_groups.txt",
            ImmutableList.of(
                "/common/cultures/00_cultures.txt", "/common/cultures/00_additional_cultures.txt"),
            "/map_data/state_regions/99_seas.txt"),
        new FileProperties.Output(
            modPath,
            "/.metadata/metadata.json",
            List.of(
                "/map_data/state_regions/00_west_europe.txt",
                "/map_data/state_regions/01_south_europe.txt",
                "/map_data/state_regions/02_east_europe.txt",
                "/map_data/state_regions/03_north_africa.txt",
                "/map_data/state_regions/04_subsaharan_africa.txt",
                "/map_data/state_regions/05_north_america.txt",
                "/map_data/state_regions/06_central_america.txt",
                "/map_data/state_regions/07_south_america.txt",
                "/map_data/state_regions/08_middle_east.txt",
                "/map_data/state_regions/09_central_asia.txt",
                "/map_data/state_regions/10_india.txt",
                "/map_data/state_regions/11_east_asia.txt",
                "/map_data/state_regions/12_indonesia.txt",
                "/map_data/state_regions/13_australasia.txt",
                "/map_data/state_regions/14_siberia.txt",
                "/common/journal_entries/01_alaska.txt",
                "/common/buildings/08_monuments.txt",
                "common/scripted_triggers/00_scripted_triggers.txt"),
            "/common/history/states/00_states.txt",
            "/common/history/pops/00_pops.txt",
            "/common/history/buildings/00_buildings.txt",
            "/common/history/countries/00_countries.txt",
            "/localization/english/00_countries_l_english.yml",
            "/common/country_definitions/00_countries.txt",
            "/common/strategic_regions/00_strategic_regions.txt",
            "/localization/english/00_strategic_regions_l_english.yml",
            "/map_data/state_regions/00_state_regions.txt",
            "/localization/english/map/00_states_l_english.yml"));
  }

  private static Metadata getMetadata() {
    return new Metadata(
        "Random World",
        "random_world",
        "0.0.1",
        "victoria3",
        ImmutableSet.of("random"),
        "1.6.*",
        "Random World",
        new Metadata.GameCustomData(true));
  }
}
