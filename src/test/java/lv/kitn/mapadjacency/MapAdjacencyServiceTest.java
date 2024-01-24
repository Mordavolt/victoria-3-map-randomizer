package lv.kitn.mapadjacency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import com.google.common.collect.ImmutableSetMultimap;
import java.awt.image.BufferedImage;
import java.util.stream.Stream;
import javax.imageio.ImageIO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

final class MapAdjacencyServiceTest {

  @Test
  void findAdjacencyMatrix() throws Exception {
    ClassLoader classLoader = getClass().getClassLoader();
    BufferedImage image =
        ImageIO.read(classLoader.getResourceAsStream("lv/kitn/mapadjacency/test_provinces.png"));
    var adjacencyMatrix = MapAdjacencyService.findAdjacencyMatrix(image);
    var black = "x000000";
    var blue = "x00A2E8";
    var orange = "xFF7F27";
    var purple = "xA349A4";
    var yellow = "xFFF200";
    var darkRed = "x880015";
    var green = "x22B14C";
    var red = "xED1C24";
    assertThat(adjacencyMatrix)
        .usingRecursiveComparison()
        .isEqualTo(
            ImmutableSetMultimap.builder()
                .put(black, blue)
                .put(black, purple)
                .put(black, red)
                .put(blue, black)
                .put(blue, darkRed)
                .put(blue, orange)
                .put(blue, purple)
                .put(blue, red)
                .put(blue, yellow)
                .put(darkRed, blue)
                .put(darkRed, green)
                .put(darkRed, purple)
                .put(darkRed, red)
                .put(darkRed, yellow)
                .put(green, darkRed)
                .put(green, orange)
                .put(green, purple)
                .put(green, yellow)
                .put(orange, blue)
                .put(orange, green)
                .put(orange, purple)
                .put(orange, yellow)
                .put(purple, black)
                .put(purple, blue)
                .put(purple, darkRed)
                .put(purple, green)
                .put(purple, orange)
                .put(purple, red)
                .put(red, black)
                .put(red, blue)
                .put(red, darkRed)
                .put(red, purple)
                .put(yellow, blue)
                .put(yellow, darkRed)
                .put(yellow, green)
                .put(yellow, orange)
                .build());
  }

  @MethodSource("provideARGBValues")
  @ParameterizedTest
  void toHex(int input, String expected) throws Exception {
    String hex = MapAdjacencyService.toHex(input);
    assertThat(hex).isEqualTo(expected);
  }

  // Providing test cases
  private static Stream<Arguments> provideARGBValues() {
    return Stream.of(
        arguments(0x7AC4D731, "xC4D731"),
        arguments(0xA2844235, "x844235"),
        arguments(0x9E04349E, "x04349E"),
        arguments(0xF57BD712, "x7BD712"),
        arguments(0x3FB2F344, "xB2F344"));
  }
}
