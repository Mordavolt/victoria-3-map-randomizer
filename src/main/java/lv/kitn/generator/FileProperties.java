package lv.kitn.generator;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("files")
record FileProperties(
    String gameInstallationPath,
    String provinceImage,
    String provinceTerrains,
    List<String> buildings,
    String buildingGroups) {}
