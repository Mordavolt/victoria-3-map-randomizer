package pdxfile; // Generated from ParadoxFile.g4 by ANTLR 4.13.1

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by {@link
 * ParadoxFileParser}.
 */
interface ParadoxFileListener extends ParseTreeListener {
  /**
   * Enter a parse tree produced by {@link ParadoxFileParser#config}.
   *
   * @param ctx the parse tree
   */
  void enterConfig(ParadoxFileParser.ConfigContext ctx);

  /**
   * Exit a parse tree produced by {@link ParadoxFileParser#config}.
   *
   * @param ctx the parse tree
   */
  void exitConfig(ParadoxFileParser.ConfigContext ctx);

  /**
   * Enter a parse tree produced by {@link ParadoxFileParser#assignment}.
   *
   * @param ctx the parse tree
   */
  void enterAssignment(ParadoxFileParser.AssignmentContext ctx);

  /**
   * Exit a parse tree produced by {@link ParadoxFileParser#assignment}.
   *
   * @param ctx the parse tree
   */
  void exitAssignment(ParadoxFileParser.AssignmentContext ctx);

  /**
   * Enter a parse tree produced by {@link ParadoxFileParser#lazy_assignment}.
   *
   * @param ctx the parse tree
   */
  void enterLazy_assignment(ParadoxFileParser.Lazy_assignmentContext ctx);

  /**
   * Exit a parse tree produced by {@link ParadoxFileParser#lazy_assignment}.
   *
   * @param ctx the parse tree
   */
  void exitLazy_assignment(ParadoxFileParser.Lazy_assignmentContext ctx);

  /**
   * Enter a parse tree produced by {@link ParadoxFileParser#field}.
   *
   * @param ctx the parse tree
   */
  void enterField(ParadoxFileParser.FieldContext ctx);

  /**
   * Exit a parse tree produced by {@link ParadoxFileParser#field}.
   *
   * @param ctx the parse tree
   */
  void exitField(ParadoxFileParser.FieldContext ctx);

  /**
   * Enter a parse tree produced by {@link ParadoxFileParser#value}.
   *
   * @param ctx the parse tree
   */
  void enterValue(ParadoxFileParser.ValueContext ctx);

  /**
   * Exit a parse tree produced by {@link ParadoxFileParser#value}.
   *
   * @param ctx the parse tree
   */
  void exitValue(ParadoxFileParser.ValueContext ctx);

  /**
   * Enter a parse tree produced by {@link ParadoxFileParser#symbol}.
   *
   * @param ctx the parse tree
   */
  void enterSymbol(ParadoxFileParser.SymbolContext ctx);

  /**
   * Exit a parse tree produced by {@link ParadoxFileParser#symbol}.
   *
   * @param ctx the parse tree
   */
  void exitSymbol(ParadoxFileParser.SymbolContext ctx);

  /**
   * Enter a parse tree produced by {@link ParadoxFileParser#string}.
   *
   * @param ctx the parse tree
   */
  void enterString(ParadoxFileParser.StringContext ctx);

  /**
   * Exit a parse tree produced by {@link ParadoxFileParser#string}.
   *
   * @param ctx the parse tree
   */
  void exitString(ParadoxFileParser.StringContext ctx);

  /**
   * Enter a parse tree produced by {@link ParadoxFileParser#integer}.
   *
   * @param ctx the parse tree
   */
  void enterInteger(ParadoxFileParser.IntegerContext ctx);

  /**
   * Exit a parse tree produced by {@link ParadoxFileParser#integer}.
   *
   * @param ctx the parse tree
   */
  void exitInteger(ParadoxFileParser.IntegerContext ctx);

  /**
   * Enter a parse tree produced by {@link ParadoxFileParser#real}.
   *
   * @param ctx the parse tree
   */
  void enterReal(ParadoxFileParser.RealContext ctx);

  /**
   * Exit a parse tree produced by {@link ParadoxFileParser#real}.
   *
   * @param ctx the parse tree
   */
  void exitReal(ParadoxFileParser.RealContext ctx);

  /**
   * Enter a parse tree produced by {@link ParadoxFileParser#date}.
   *
   * @param ctx the parse tree
   */
  void enterDate(ParadoxFileParser.DateContext ctx);

  /**
   * Exit a parse tree produced by {@link ParadoxFileParser#date}.
   *
   * @param ctx the parse tree
   */
  void exitDate(ParadoxFileParser.DateContext ctx);

  /**
   * Enter a parse tree produced by {@link ParadoxFileParser#percent}.
   *
   * @param ctx the parse tree
   */
  void enterPercent(ParadoxFileParser.PercentContext ctx);

  /**
   * Exit a parse tree produced by {@link ParadoxFileParser#percent}.
   *
   * @param ctx the parse tree
   */
  void exitPercent(ParadoxFileParser.PercentContext ctx);

  /**
   * Enter a parse tree produced by {@link ParadoxFileParser#map}.
   *
   * @param ctx the parse tree
   */
  void enterMap(ParadoxFileParser.MapContext ctx);

  /**
   * Exit a parse tree produced by {@link ParadoxFileParser#map}.
   *
   * @param ctx the parse tree
   */
  void exitMap(ParadoxFileParser.MapContext ctx);

  /**
   * Enter a parse tree produced by {@link ParadoxFileParser#array}.
   *
   * @param ctx the parse tree
   */
  void enterArray(ParadoxFileParser.ArrayContext ctx);

  /**
   * Exit a parse tree produced by {@link ParadoxFileParser#array}.
   *
   * @param ctx the parse tree
   */
  void exitArray(ParadoxFileParser.ArrayContext ctx);

  /**
   * Enter a parse tree produced by {@link ParadoxFileParser#variable}.
   *
   * @param ctx the parse tree
   */
  void enterVariable(ParadoxFileParser.VariableContext ctx);

  /**
   * Exit a parse tree produced by {@link ParadoxFileParser#variable}.
   *
   * @param ctx the parse tree
   */
  void exitVariable(ParadoxFileParser.VariableContext ctx);

  /**
   * Enter a parse tree produced by {@link ParadoxFileParser#variable_expression}.
   *
   * @param ctx the parse tree
   */
  void enterVariable_expression(ParadoxFileParser.Variable_expressionContext ctx);

  /**
   * Exit a parse tree produced by {@link ParadoxFileParser#variable_expression}.
   *
   * @param ctx the parse tree
   */
  void exitVariable_expression(ParadoxFileParser.Variable_expressionContext ctx);

  /**
   * Enter a parse tree produced by {@link ParadoxFileParser#expression}.
   *
   * @param ctx the parse tree
   */
  void enterExpression(ParadoxFileParser.ExpressionContext ctx);

  /**
   * Exit a parse tree produced by {@link ParadoxFileParser#expression}.
   *
   * @param ctx the parse tree
   */
  void exitExpression(ParadoxFileParser.ExpressionContext ctx);

  /**
   * Enter a parse tree produced by {@link ParadoxFileParser#list}.
   *
   * @param ctx the parse tree
   */
  void enterList(ParadoxFileParser.ListContext ctx);

  /**
   * Exit a parse tree produced by {@link ParadoxFileParser#list}.
   *
   * @param ctx the parse tree
   */
  void exitList(ParadoxFileParser.ListContext ctx);

  /**
   * Enter a parse tree produced by {@link ParadoxFileParser#constructor}.
   *
   * @param ctx the parse tree
   */
  void enterConstructor(ParadoxFileParser.ConstructorContext ctx);

  /**
   * Exit a parse tree produced by {@link ParadoxFileParser#constructor}.
   *
   * @param ctx the parse tree
   */
  void exitConstructor(ParadoxFileParser.ConstructorContext ctx);
}
