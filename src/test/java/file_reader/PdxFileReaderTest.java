package file_reader;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import org.antlr.v4.runtime.CharStreams;
import org.junit.jupiter.api.Test;

class PdxFileReaderTest {

  @Test
  void parseLines() throws Exception {
    // TODO the nested array only works with odd number of elements, even numbers are parsed as
    // item1=item2. FML.
    // Given
    var nestedConfig =
        """
              aaa = {
                  prop1 = abc
                  prop2 = "ccc"

                  nested_array = {
                      item1
                      item2
                      item3
                  }

                  nested_prop = {
                      item1 = value1
                  }

                  prop3 = no
                  prop4 = 0.30
              }

              bbb = {
                  prop1 = something
              }
              """;

    // When
    var parsedMap = PdxFileReader.parseFileToDataMap(CharStreams.fromString(nestedConfig));

    // Then
    assertThat(parsedMap)
        .isEqualTo(
            Map.of(
                "aaa",
                Map.of(
                    "prop1",
                    "abc",
                    "prop2",
                    "\"ccc\"",
                    "nested_array",
                    List.of("item1", "item2", "item3"),
                    "nested_prop",
                    Map.of("item1", "value1"),
                    "prop3",
                    "no",
                    "prop4",
                    0.3),
                "bbb",
                Map.of("prop1", "something")));
  }
}
