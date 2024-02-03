package lv.kitn.pdxfile;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static com.google.common.collect.ImmutableMap.toImmutableMap;

import java.util.Map;
import lv.kitn.pdxfile.ParadoxFileParser.ValueContext;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;

public class PdxFileReader {
  public static Map<String, Object> parseFileToDataMap(CharStream charStream) {
    ParadoxFileLexer lexer = new ParadoxFileLexer(charStream);
    lexer.removeErrorListeners();
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    ParadoxFileParser parser = new ParadoxFileParser(tokens);
    return parser.config().lazy_assignment().stream()
        .collect(
            toImmutableMap(
                lazyAssignment -> lazyAssignment.assignment().field().getText(),
                lazyAssignment -> getValue(lazyAssignment.assignment().value())));
  }

  private static Object getValue(ValueContext value) {
    if (value.integer() != null) {
      return Integer.parseInt(value.integer().getText());
    } else if (value.real() != null) {
      return Double.parseDouble(value.real().getText());
    } else if (value.date() != null) {
      return value.date().getText();
    } else if (value.percent() != null) {
      return Integer.parseInt(value.percent().getText().replaceAll("%", ""));
    } else if (value.string() != null) {
      return value.string().getText();
    } else if (value.symbol() != null) {
      return value.symbol().getText();
    } else if (value.variable() != null) {
      return value.variable().getText();
    } else if (value.variable_expression() != null) {
      return value.variable_expression().getText();
    } else if (value.map() != null) {
      return value.map().assignment().stream()
          .collect(
              toImmutableMap(
                  assignment -> assignment.field().getText(),
                  assignment -> getValue(assignment.value()),
                  (a, b) -> {
                    // We have duplicates in config files :(
                    return a;
                  }));
    } else if (value.array() != null) {
      return value.array().value().stream().map(PdxFileReader::getValue).collect(toImmutableList());
    } else if (value.list() != null) {
      throw new UnsupportedOperationException("A list config not supported");
    } else if (value.constructor() != null) {
      throw new UnsupportedOperationException("A constructor config not supported");
    }
    throw new IllegalStateException("This should never happen");
  }
}
