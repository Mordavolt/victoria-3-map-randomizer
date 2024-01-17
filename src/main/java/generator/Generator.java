package generator;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Generator {

  public static void main(String[] args) {
    new SpringApplicationBuilder(Generator.class).run(args);
  }
}
