package lv.kitn.generator;

import java.util.Random;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(FileProperties.class)
@Configuration
class GeneratorConfig {
  @Bean
  Random random() {
    return new Random();
  }
}
