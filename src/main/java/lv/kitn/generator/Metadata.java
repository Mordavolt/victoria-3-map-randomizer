package lv.kitn.generator;

import com.google.common.collect.ImmutableSet;

record Metadata(
    String name,
    String id,
    String version,
    String gameId,
    ImmutableSet<String> tags,
    String supportedGameVersion,
    String shortDescription,
    GameCustomData gameCustomData) {
  record GameCustomData(boolean multiplayerSynchronized) {}
}
