package lv.kitn.generator;

public enum Terrain {
  DESERT,
  FOREST,
  HILLS,
  JUNGLE,
  LAKES,
  MOUNTAIN,
  OCEAN,
  PLAINS,
  SAVANNA,
  SNOW,
  TUNDRA,
  WETLAND;

  public boolean isLand() {
    return this != OCEAN && this != LAKES;
  }
}
