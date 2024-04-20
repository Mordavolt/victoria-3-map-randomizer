package lv.kitn.generator;

import java.util.List;

record Metadata(
    String name,
    String id,
    String version,
    String gameId,
    List<String> tags,
    String supportedGameVersion,
    String shortDescription,
    GameCustomData gameCustomData) {
  record GameCustomData(boolean multiplayerSynchronized) {}
}
