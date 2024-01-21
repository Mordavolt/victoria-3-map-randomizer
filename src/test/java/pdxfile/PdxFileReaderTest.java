package pdxfile;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.antlr.v4.runtime.CharStreams;
import org.junit.jupiter.api.Test;

final class PdxFileReaderTest {

  @Test
  void parseLines() throws Exception {

    var nestedConfig =
        """
              aaa = {
                  prop1 = abc
                  prop2 = "ccc"

                  nested_array = {
                      item1
                      item2
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

    var parsedMap = PdxFileReader.parseFileToDataMap(CharStreams.fromString(nestedConfig));

    assertThat(parsedMap)
        .isEqualTo(
            ImmutableMap.of(
                "aaa",
                    ImmutableMap.of(
                        "prop1",
                        "abc",
                        "prop2",
                        "\"ccc\"",
                        "nested_array",
                        ImmutableList.of("item1", "item2"),
                        "nested_prop",
                        ImmutableMap.of("item1", "value1"),
                        "prop3",
                        "no",
                        "prop4",
                        0.3),
                "bbb", ImmutableMap.of("prop1", "something")));
  }
}
