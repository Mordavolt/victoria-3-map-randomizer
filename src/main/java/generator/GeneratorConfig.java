package generator;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(FileProperties.class)
@Configuration
class GeneratorConfig {}
