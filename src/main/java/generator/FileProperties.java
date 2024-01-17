package generator;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("files")
record FileProperties(
    String gameInstallationPath,
    String provinceTerrains,
    List<String> buildings,
    String buildingGroups) {}
