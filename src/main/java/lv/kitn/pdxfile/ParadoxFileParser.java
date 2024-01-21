package lv.kitn.pdxfile; // Generated from ParadoxFile.g4 by ANTLR 4.13.1

import java.util.List;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
class ParadoxFileParser extends Parser {
  static {
    RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION);
  }

  protected static final DFA[] _decisionToDFA;
  protected static final PredictionContextCache _sharedContextCache = new PredictionContextCache();
  public static final int OPERATOR = 1,
      BLOCK_START = 2,
      BLOCK_END = 3,
      VARIABLE_START = 4,
      VARIABLE_EXPRESSION_START = 5,
      VARIABLE_EXPRESSION_END = 6,
      ABS_VALUE = 7,
      OPEN_PARENS = 8,
      CLOSE_PARENS = 9,
      LIST_START = 10,
      INT = 11,
      PCT = 12,
      REAL = 13,
      DATE = 14,
      SSTRING = 15,
      DSTRING = 16,
      CSTRING = 17,
      SYMBOL = 18,
      PLUS_MINUS = 19,
      MULTIPLY_DIVIDE = 20,
      COMMA = 21,
      WHITESPACE = 22,
      LINE_COMMENT = 23;
  public static final int RULE_config = 0,
      RULE_assignment = 1,
      RULE_lazy_assignment = 2,
      RULE_field = 3,
      RULE_value = 4,
      RULE_symbol = 5,
      RULE_string = 6,
      RULE_integer = 7,
      RULE_real = 8,
      RULE_date = 9,
      RULE_percent = 10,
      RULE_map = 11,
      RULE_array = 12,
      RULE_variable = 13,
      RULE_variable_expression = 14,
      RULE_expression = 15,
      RULE_list = 16,
      RULE_constructor = 17;

  private static String[] makeRuleNames() {
    return new String[] {
      "config",
      "assignment",
      "lazy_assignment",
      "field",
      "value",
      "symbol",
      "string",
      "integer",
      "real",
      "date",
      "percent",
      "map",
      "array",
      "variable",
      "variable_expression",
      "expression",
      "list",
      "constructor"
    };
  }

  public static final String[] ruleNames = makeRuleNames();

  private static String[] makeLiteralNames() {
    return new String[] {
      null, null, "'{'", "'}'", null, "'['", "']'", "'|'", "'('", "')'", "'list'", null, null, null,
      null, null, null, null, null, null, null, "','"
    };
  }

  private static final String[] _LITERAL_NAMES = makeLiteralNames();

  private static String[] makeSymbolicNames() {
    return new String[] {
      null,
      "OPERATOR",
      "BLOCK_START",
      "BLOCK_END",
      "VARIABLE_START",
      "VARIABLE_EXPRESSION_START",
      "VARIABLE_EXPRESSION_END",
      "ABS_VALUE",
      "OPEN_PARENS",
      "CLOSE_PARENS",
      "LIST_START",
      "INT",
      "PCT",
      "REAL",
      "DATE",
      "SSTRING",
      "DSTRING",
      "CSTRING",
      "SYMBOL",
      "PLUS_MINUS",
      "MULTIPLY_DIVIDE",
      "COMMA",
      "WHITESPACE",
      "LINE_COMMENT"
    };
  }

  private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
  public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

  /**
   * @deprecated Use {@link #VOCABULARY} instead.
   */
  @Deprecated public static final String[] tokenNames;

  static {
    tokenNames = new String[_SYMBOLIC_NAMES.length];
    for (int i = 0; i < tokenNames.length; i++) {
      tokenNames[i] = VOCABULARY.getLiteralName(i);
      if (tokenNames[i] == null) {
        tokenNames[i] = VOCABULARY.getSymbolicName(i);
      }

      if (tokenNames[i] == null) {
        tokenNames[i] = "<INVALID>";
      }
    }
  }

  @Override
  @Deprecated
  public String[] getTokenNames() {
    return tokenNames;
  }

  @Override
  public Vocabulary getVocabulary() {
    return VOCABULARY;
  }

  @Override
  public String getGrammarFileName() {
    return "ParadoxFile.g4";
  }

  @Override
  public String[] getRuleNames() {
    return ruleNames;
  }

  @Override
  public String getSerializedATN() {
    return _serializedATN;
  }

  @Override
  public ATN getATN() {
    return _ATN;
  }

  public ParadoxFileParser(TokenStream input) {
    super(input);
    _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
  }

  @SuppressWarnings("CheckReturnValue")
  public static class ConfigContext extends ParserRuleContext {
    public List<Lazy_assignmentContext> lazy_assignment() {
      return getRuleContexts(Lazy_assignmentContext.class);
    }

    public Lazy_assignmentContext lazy_assignment(int i) {
      return getRuleContext(Lazy_assignmentContext.class, i);
    }

    public ConfigContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_config;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof ParadoxFileListener)
        ((ParadoxFileListener) listener).enterConfig(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof ParadoxFileListener)
        ((ParadoxFileListener) listener).exitConfig(this);
    }
  }

  public final ConfigContext config() throws RecognitionException {
    ConfigContext _localctx = new ConfigContext(_ctx, getState());
    enterRule(_localctx, 0, RULE_config);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(39);
        _errHandler.sync(this);
        _la = _input.LA(1);
        while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 502800L) != 0)) {
          {
            {
              setState(36);
              lazy_assignment();
            }
          }
          setState(41);
          _errHandler.sync(this);
          _la = _input.LA(1);
        }
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class AssignmentContext extends ParserRuleContext {
    public FieldContext field() {
      return getRuleContext(FieldContext.class, 0);
    }

    public TerminalNode OPERATOR() {
      return getToken(ParadoxFileParser.OPERATOR, 0);
    }

    public ValueContext value() {
      return getRuleContext(ValueContext.class, 0);
    }

    public AssignmentContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_assignment;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof ParadoxFileListener)
        ((ParadoxFileListener) listener).enterAssignment(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof ParadoxFileListener)
        ((ParadoxFileListener) listener).exitAssignment(this);
    }
  }

  public final AssignmentContext assignment() throws RecognitionException {
    AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
    enterRule(_localctx, 2, RULE_assignment);
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(42);
        field();
        setState(43);
        match(OPERATOR);
        setState(44);
        value();
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class Lazy_assignmentContext extends ParserRuleContext {
    public AssignmentContext assignment() {
      return getRuleContext(AssignmentContext.class, 0);
    }

    public FieldContext field() {
      return getRuleContext(FieldContext.class, 0);
    }

    public ValueContext value() {
      return getRuleContext(ValueContext.class, 0);
    }

    public Lazy_assignmentContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_lazy_assignment;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof ParadoxFileListener)
        ((ParadoxFileListener) listener).enterLazy_assignment(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof ParadoxFileListener)
        ((ParadoxFileListener) listener).exitLazy_assignment(this);
    }
  }

  public final Lazy_assignmentContext lazy_assignment() throws RecognitionException {
    Lazy_assignmentContext _localctx = new Lazy_assignmentContext(_ctx, getState());
    enterRule(_localctx, 4, RULE_lazy_assignment);
    try {
      setState(50);
      _errHandler.sync(this);
      switch (getInterpreter().adaptivePredict(_input, 1, _ctx)) {
        case 1:
          enterOuterAlt(_localctx, 1);
          {
            setState(46);
            assignment();
          }
          break;
        case 2:
          enterOuterAlt(_localctx, 2);
          {
            setState(47);
            field();
            setState(48);
            value();
          }
          break;
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class FieldContext extends ParserRuleContext {
    public StringContext string() {
      return getRuleContext(StringContext.class, 0);
    }

    public SymbolContext symbol() {
      return getRuleContext(SymbolContext.class, 0);
    }

    public VariableContext variable() {
      return getRuleContext(VariableContext.class, 0);
    }

    public TerminalNode LIST_START() {
      return getToken(ParadoxFileParser.LIST_START, 0);
    }

    public FieldContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_field;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof ParadoxFileListener)
        ((ParadoxFileListener) listener).enterField(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof ParadoxFileListener) ((ParadoxFileListener) listener).exitField(this);
    }
  }

  public final FieldContext field() throws RecognitionException {
    FieldContext _localctx = new FieldContext(_ctx, getState());
    enterRule(_localctx, 6, RULE_field);
    try {
      setState(56);
      _errHandler.sync(this);
      switch (getInterpreter().adaptivePredict(_input, 2, _ctx)) {
        case 1:
          enterOuterAlt(_localctx, 1);
          {
            setState(52);
            string();
          }
          break;
        case 2:
          enterOuterAlt(_localctx, 2);
          {
            setState(53);
            symbol();
          }
          break;
        case 3:
          enterOuterAlt(_localctx, 3);
          {
            setState(54);
            variable();
          }
          break;
        case 4:
          enterOuterAlt(_localctx, 4);
          {
            setState(55);
            match(LIST_START);
          }
          break;
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class ValueContext extends ParserRuleContext {
    public IntegerContext integer() {
      return getRuleContext(IntegerContext.class, 0);
    }

    public PercentContext percent() {
      return getRuleContext(PercentContext.class, 0);
    }

    public RealContext real() {
      return getRuleContext(RealContext.class, 0);
    }

    public DateContext date() {
      return getRuleContext(DateContext.class, 0);
    }

    public StringContext string() {
      return getRuleContext(StringContext.class, 0);
    }

    public SymbolContext symbol() {
      return getRuleContext(SymbolContext.class, 0);
    }

    public VariableContext variable() {
      return getRuleContext(VariableContext.class, 0);
    }

    public Variable_expressionContext variable_expression() {
      return getRuleContext(Variable_expressionContext.class, 0);
    }

    public MapContext map() {
      return getRuleContext(MapContext.class, 0);
    }

    public ArrayContext array() {
      return getRuleContext(ArrayContext.class, 0);
    }

    public ListContext list() {
      return getRuleContext(ListContext.class, 0);
    }

    public ConstructorContext constructor() {
      return getRuleContext(ConstructorContext.class, 0);
    }

    public ValueContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_value;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof ParadoxFileListener)
        ((ParadoxFileListener) listener).enterValue(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof ParadoxFileListener) ((ParadoxFileListener) listener).exitValue(this);
    }
  }

  public final ValueContext value() throws RecognitionException {
    ValueContext _localctx = new ValueContext(_ctx, getState());
    enterRule(_localctx, 8, RULE_value);
    try {
      setState(70);
      _errHandler.sync(this);
      switch (getInterpreter().adaptivePredict(_input, 3, _ctx)) {
        case 1:
          enterOuterAlt(_localctx, 1);
          {
            setState(58);
            integer();
          }
          break;
        case 2:
          enterOuterAlt(_localctx, 2);
          {
            setState(59);
            percent();
          }
          break;
        case 3:
          enterOuterAlt(_localctx, 3);
          {
            setState(60);
            real();
          }
          break;
        case 4:
          enterOuterAlt(_localctx, 4);
          {
            setState(61);
            date();
          }
          break;
        case 5:
          enterOuterAlt(_localctx, 5);
          {
            setState(62);
            string();
          }
          break;
        case 6:
          enterOuterAlt(_localctx, 6);
          {
            setState(63);
            symbol();
          }
          break;
        case 7:
          enterOuterAlt(_localctx, 7);
          {
            setState(64);
            variable();
          }
          break;
        case 8:
          enterOuterAlt(_localctx, 8);
          {
            setState(65);
            variable_expression();
          }
          break;
        case 9:
          enterOuterAlt(_localctx, 9);
          {
            setState(66);
            map();
          }
          break;
        case 10:
          enterOuterAlt(_localctx, 10);
          {
            setState(67);
            array();
          }
          break;
        case 11:
          enterOuterAlt(_localctx, 11);
          {
            setState(68);
            list();
          }
          break;
        case 12:
          enterOuterAlt(_localctx, 12);
          {
            setState(69);
            constructor();
          }
          break;
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class SymbolContext extends ParserRuleContext {
    public StringContext string() {
      return getRuleContext(StringContext.class, 0);
    }

    public TerminalNode INT() {
      return getToken(ParadoxFileParser.INT, 0);
    }

    public TerminalNode REAL() {
      return getToken(ParadoxFileParser.REAL, 0);
    }

    public TerminalNode SYMBOL() {
      return getToken(ParadoxFileParser.SYMBOL, 0);
    }

    public SymbolContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_symbol;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof ParadoxFileListener)
        ((ParadoxFileListener) listener).enterSymbol(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof ParadoxFileListener)
        ((ParadoxFileListener) listener).exitSymbol(this);
    }
  }

  public final SymbolContext symbol() throws RecognitionException {
    SymbolContext _localctx = new SymbolContext(_ctx, getState());
    enterRule(_localctx, 10, RULE_symbol);
    try {
      setState(76);
      _errHandler.sync(this);
      switch (_input.LA(1)) {
        case SSTRING:
        case DSTRING:
        case CSTRING:
          enterOuterAlt(_localctx, 1);
          {
            setState(72);
            string();
          }
          break;
        case INT:
          enterOuterAlt(_localctx, 2);
          {
            setState(73);
            match(INT);
          }
          break;
        case REAL:
          enterOuterAlt(_localctx, 3);
          {
            setState(74);
            match(REAL);
          }
          break;
        case SYMBOL:
          enterOuterAlt(_localctx, 4);
          {
            setState(75);
            match(SYMBOL);
          }
          break;
        default:
          throw new NoViableAltException(this);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class StringContext extends ParserRuleContext {
    public TerminalNode DSTRING() {
      return getToken(ParadoxFileParser.DSTRING, 0);
    }

    public TerminalNode SSTRING() {
      return getToken(ParadoxFileParser.SSTRING, 0);
    }

    public TerminalNode CSTRING() {
      return getToken(ParadoxFileParser.CSTRING, 0);
    }

    public StringContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_string;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof ParadoxFileListener)
        ((ParadoxFileListener) listener).enterString(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof ParadoxFileListener)
        ((ParadoxFileListener) listener).exitString(this);
    }
  }

  public final StringContext string() throws RecognitionException {
    StringContext _localctx = new StringContext(_ctx, getState());
    enterRule(_localctx, 12, RULE_string);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(78);
        _la = _input.LA(1);
        if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & 229376L) != 0))) {
          _errHandler.recoverInline(this);
        } else {
          if (_input.LA(1) == Token.EOF) matchedEOF = true;
          _errHandler.reportMatch(this);
          consume();
        }
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class IntegerContext extends ParserRuleContext {
    public TerminalNode INT() {
      return getToken(ParadoxFileParser.INT, 0);
    }

    public IntegerContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_integer;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof ParadoxFileListener)
        ((ParadoxFileListener) listener).enterInteger(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof ParadoxFileListener)
        ((ParadoxFileListener) listener).exitInteger(this);
    }
  }

  public final IntegerContext integer() throws RecognitionException {
    IntegerContext _localctx = new IntegerContext(_ctx, getState());
    enterRule(_localctx, 14, RULE_integer);
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(80);
        match(INT);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class RealContext extends ParserRuleContext {
    public TerminalNode REAL() {
      return getToken(ParadoxFileParser.REAL, 0);
    }

    public RealContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_real;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof ParadoxFileListener) ((ParadoxFileListener) listener).enterReal(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof ParadoxFileListener) ((ParadoxFileListener) listener).exitReal(this);
    }
  }

  public final RealContext real() throws RecognitionException {
    RealContext _localctx = new RealContext(_ctx, getState());
    enterRule(_localctx, 16, RULE_real);
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(82);
        match(REAL);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class DateContext extends ParserRuleContext {
    public TerminalNode DATE() {
      return getToken(ParadoxFileParser.DATE, 0);
    }

    public DateContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_date;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof ParadoxFileListener) ((ParadoxFileListener) listener).enterDate(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof ParadoxFileListener) ((ParadoxFileListener) listener).exitDate(this);
    }
  }

  public final DateContext date() throws RecognitionException {
    DateContext _localctx = new DateContext(_ctx, getState());
    enterRule(_localctx, 18, RULE_date);
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(84);
        match(DATE);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class PercentContext extends ParserRuleContext {
    public TerminalNode PCT() {
      return getToken(ParadoxFileParser.PCT, 0);
    }

    public PercentContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_percent;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof ParadoxFileListener)
        ((ParadoxFileListener) listener).enterPercent(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof ParadoxFileListener)
        ((ParadoxFileListener) listener).exitPercent(this);
    }
  }

  public final PercentContext percent() throws RecognitionException {
    PercentContext _localctx = new PercentContext(_ctx, getState());
    enterRule(_localctx, 20, RULE_percent);
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(86);
        match(PCT);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class MapContext extends ParserRuleContext {
    public TerminalNode BLOCK_START() {
      return getToken(ParadoxFileParser.BLOCK_START, 0);
    }

    public TerminalNode BLOCK_END() {
      return getToken(ParadoxFileParser.BLOCK_END, 0);
    }

    public List<AssignmentContext> assignment() {
      return getRuleContexts(AssignmentContext.class);
    }

    public AssignmentContext assignment(int i) {
      return getRuleContext(AssignmentContext.class, i);
    }

    public MapContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_map;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof ParadoxFileListener) ((ParadoxFileListener) listener).enterMap(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof ParadoxFileListener) ((ParadoxFileListener) listener).exitMap(this);
    }
  }

  public final MapContext map() throws RecognitionException {
    MapContext _localctx = new MapContext(_ctx, getState());
    enterRule(_localctx, 22, RULE_map);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(88);
        match(BLOCK_START);
        setState(92);
        _errHandler.sync(this);
        _la = _input.LA(1);
        while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 502800L) != 0)) {
          {
            {
              setState(89);
              assignment();
            }
          }
          setState(94);
          _errHandler.sync(this);
          _la = _input.LA(1);
        }
        setState(95);
        match(BLOCK_END);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class ArrayContext extends ParserRuleContext {
    public TerminalNode BLOCK_START() {
      return getToken(ParadoxFileParser.BLOCK_START, 0);
    }

    public List<ValueContext> value() {
      return getRuleContexts(ValueContext.class);
    }

    public ValueContext value(int i) {
      return getRuleContext(ValueContext.class, i);
    }

    public TerminalNode BLOCK_END() {
      return getToken(ParadoxFileParser.BLOCK_END, 0);
    }

    public List<TerminalNode> COMMA() {
      return getTokens(ParadoxFileParser.COMMA);
    }

    public TerminalNode COMMA(int i) {
      return getToken(ParadoxFileParser.COMMA, i);
    }

    public List<AssignmentContext> assignment() {
      return getRuleContexts(AssignmentContext.class);
    }

    public AssignmentContext assignment(int i) {
      return getRuleContext(AssignmentContext.class, i);
    }

    public ArrayContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_array;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof ParadoxFileListener)
        ((ParadoxFileListener) listener).enterArray(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof ParadoxFileListener) ((ParadoxFileListener) listener).exitArray(this);
    }
  }

  public final ArrayContext array() throws RecognitionException {
    ArrayContext _localctx = new ArrayContext(_ctx, getState());
    enterRule(_localctx, 24, RULE_array);
    int _la;
    try {
      int _alt;
      setState(143);
      _errHandler.sync(this);
      switch (getInterpreter().adaptivePredict(_input, 16, _ctx)) {
        case 1:
          enterOuterAlt(_localctx, 1);
          {
            setState(97);
            match(BLOCK_START);
            setState(98);
            value();
            setState(100);
            _errHandler.sync(this);
            _la = _input.LA(1);
            if (_la == COMMA) {
              {
                setState(99);
                match(COMMA);
              }
            }

            setState(111);
            _errHandler.sync(this);
            _la = _input.LA(1);
            while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 523284L) != 0)) {
              {
                {
                  setState(104);
                  _errHandler.sync(this);
                  switch (getInterpreter().adaptivePredict(_input, 7, _ctx)) {
                    case 1:
                      {
                        setState(102);
                        value();
                      }
                      break;
                    case 2:
                      {
                        setState(103);
                        assignment();
                      }
                      break;
                  }
                  setState(107);
                  _errHandler.sync(this);
                  _la = _input.LA(1);
                  if (_la == COMMA) {
                    {
                      setState(106);
                      match(COMMA);
                    }
                  }
                }
              }
              setState(113);
              _errHandler.sync(this);
              _la = _input.LA(1);
            }
            setState(114);
            match(BLOCK_END);
          }
          break;
        case 2:
          enterOuterAlt(_localctx, 2);
          {
            setState(116);
            match(BLOCK_START);
            setState(121);
            _errHandler.sync(this);
            _alt = 1;
            do {
              switch (_alt) {
                case 1:
                  {
                    {
                      setState(117);
                      assignment();
                      setState(119);
                      _errHandler.sync(this);
                      _la = _input.LA(1);
                      if (_la == COMMA) {
                        {
                          setState(118);
                          match(COMMA);
                        }
                      }
                    }
                  }
                  break;
                default:
                  throw new NoViableAltException(this);
              }
              setState(123);
              _errHandler.sync(this);
              _alt = getInterpreter().adaptivePredict(_input, 11, _ctx);
            } while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER);
            setState(125);
            value();
            setState(127);
            _errHandler.sync(this);
            _la = _input.LA(1);
            if (_la == COMMA) {
              {
                setState(126);
                match(COMMA);
              }
            }

            setState(138);
            _errHandler.sync(this);
            _la = _input.LA(1);
            while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 523284L) != 0)) {
              {
                {
                  setState(131);
                  _errHandler.sync(this);
                  switch (getInterpreter().adaptivePredict(_input, 13, _ctx)) {
                    case 1:
                      {
                        setState(129);
                        value();
                      }
                      break;
                    case 2:
                      {
                        setState(130);
                        assignment();
                      }
                      break;
                  }
                  setState(134);
                  _errHandler.sync(this);
                  _la = _input.LA(1);
                  if (_la == COMMA) {
                    {
                      setState(133);
                      match(COMMA);
                    }
                  }
                }
              }
              setState(140);
              _errHandler.sync(this);
              _la = _input.LA(1);
            }
            setState(141);
            match(BLOCK_END);
          }
          break;
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class VariableContext extends ParserRuleContext {
    public TerminalNode VARIABLE_START() {
      return getToken(ParadoxFileParser.VARIABLE_START, 0);
    }

    public TerminalNode SYMBOL() {
      return getToken(ParadoxFileParser.SYMBOL, 0);
    }

    public TerminalNode INT() {
      return getToken(ParadoxFileParser.INT, 0);
    }

    public VariableContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_variable;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof ParadoxFileListener)
        ((ParadoxFileListener) listener).enterVariable(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof ParadoxFileListener)
        ((ParadoxFileListener) listener).exitVariable(this);
    }
  }

  public final VariableContext variable() throws RecognitionException {
    VariableContext _localctx = new VariableContext(_ctx, getState());
    enterRule(_localctx, 26, RULE_variable);
    try {
      setState(149);
      _errHandler.sync(this);
      switch (getInterpreter().adaptivePredict(_input, 17, _ctx)) {
        case 1:
          enterOuterAlt(_localctx, 1);
          {
            setState(145);
            match(VARIABLE_START);
            setState(146);
            match(SYMBOL);
          }
          break;
        case 2:
          enterOuterAlt(_localctx, 2);
          {
            setState(147);
            match(VARIABLE_START);
            setState(148);
            match(INT);
          }
          break;
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class Variable_expressionContext extends ParserRuleContext {
    public TerminalNode VARIABLE_START() {
      return getToken(ParadoxFileParser.VARIABLE_START, 0);
    }

    public TerminalNode VARIABLE_EXPRESSION_START() {
      return getToken(ParadoxFileParser.VARIABLE_EXPRESSION_START, 0);
    }

    public ExpressionContext expression() {
      return getRuleContext(ExpressionContext.class, 0);
    }

    public TerminalNode VARIABLE_EXPRESSION_END() {
      return getToken(ParadoxFileParser.VARIABLE_EXPRESSION_END, 0);
    }

    public Variable_expressionContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_variable_expression;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof ParadoxFileListener)
        ((ParadoxFileListener) listener).enterVariable_expression(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof ParadoxFileListener)
        ((ParadoxFileListener) listener).exitVariable_expression(this);
    }
  }

  public final Variable_expressionContext variable_expression() throws RecognitionException {
    Variable_expressionContext _localctx = new Variable_expressionContext(_ctx, getState());
    enterRule(_localctx, 28, RULE_variable_expression);
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(151);
        match(VARIABLE_START);
        setState(152);
        match(VARIABLE_EXPRESSION_START);
        setState(153);
        expression(0);
        setState(154);
        match(VARIABLE_EXPRESSION_END);
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class ExpressionContext extends ParserRuleContext {
    public List<TerminalNode> ABS_VALUE() {
      return getTokens(ParadoxFileParser.ABS_VALUE);
    }

    public TerminalNode ABS_VALUE(int i) {
      return getToken(ParadoxFileParser.ABS_VALUE, i);
    }

    public List<ExpressionContext> expression() {
      return getRuleContexts(ExpressionContext.class);
    }

    public ExpressionContext expression(int i) {
      return getRuleContext(ExpressionContext.class, i);
    }

    public TerminalNode OPEN_PARENS() {
      return getToken(ParadoxFileParser.OPEN_PARENS, 0);
    }

    public TerminalNode CLOSE_PARENS() {
      return getToken(ParadoxFileParser.CLOSE_PARENS, 0);
    }

    public TerminalNode PLUS_MINUS() {
      return getToken(ParadoxFileParser.PLUS_MINUS, 0);
    }

    public ValueContext value() {
      return getRuleContext(ValueContext.class, 0);
    }

    public TerminalNode MULTIPLY_DIVIDE() {
      return getToken(ParadoxFileParser.MULTIPLY_DIVIDE, 0);
    }

    public ExpressionContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_expression;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof ParadoxFileListener)
        ((ParadoxFileListener) listener).enterExpression(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof ParadoxFileListener)
        ((ParadoxFileListener) listener).exitExpression(this);
    }
  }

  public final ExpressionContext expression() throws RecognitionException {
    return expression(0);
  }

  private ExpressionContext expression(int _p) throws RecognitionException {
    ParserRuleContext _parentctx = _ctx;
    int _parentState = getState();
    ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
    ExpressionContext _prevctx = _localctx;
    int _startState = 30;
    enterRecursionRule(_localctx, 30, RULE_expression, _p);
    try {
      int _alt;
      enterOuterAlt(_localctx, 1);
      {
        setState(168);
        _errHandler.sync(this);
        switch (_input.LA(1)) {
          case ABS_VALUE:
            {
              setState(157);
              match(ABS_VALUE);
              setState(158);
              expression(0);
              setState(159);
              match(ABS_VALUE);
            }
            break;
          case OPEN_PARENS:
            {
              setState(161);
              match(OPEN_PARENS);
              setState(162);
              expression(0);
              setState(163);
              match(CLOSE_PARENS);
            }
            break;
          case PLUS_MINUS:
            {
              setState(165);
              match(PLUS_MINUS);
              setState(166);
              value();
            }
            break;
          case BLOCK_START:
          case VARIABLE_START:
          case LIST_START:
          case INT:
          case PCT:
          case REAL:
          case DATE:
          case SSTRING:
          case DSTRING:
          case CSTRING:
          case SYMBOL:
            {
              setState(167);
              value();
            }
            break;
          default:
            throw new NoViableAltException(this);
        }
        _ctx.stop = _input.LT(-1);
        setState(178);
        _errHandler.sync(this);
        _alt = getInterpreter().adaptivePredict(_input, 20, _ctx);
        while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
          if (_alt == 1) {
            if (_parseListeners != null) triggerExitRuleEvent();
            _prevctx = _localctx;
            {
              setState(176);
              _errHandler.sync(this);
              switch (getInterpreter().adaptivePredict(_input, 19, _ctx)) {
                case 1:
                  {
                    _localctx = new ExpressionContext(_parentctx, _parentState);
                    pushNewRecursionContext(_localctx, _startState, RULE_expression);
                    setState(170);
                    if (!(precpred(_ctx, 6)))
                      throw new FailedPredicateException(this, "precpred(_ctx, 6)");
                    setState(171);
                    match(MULTIPLY_DIVIDE);
                    setState(172);
                    expression(7);
                  }
                  break;
                case 2:
                  {
                    _localctx = new ExpressionContext(_parentctx, _parentState);
                    pushNewRecursionContext(_localctx, _startState, RULE_expression);
                    setState(173);
                    if (!(precpred(_ctx, 5)))
                      throw new FailedPredicateException(this, "precpred(_ctx, 5)");
                    setState(174);
                    match(PLUS_MINUS);
                    setState(175);
                    expression(6);
                  }
                  break;
              }
            }
          }
          setState(180);
          _errHandler.sync(this);
          _alt = getInterpreter().adaptivePredict(_input, 20, _ctx);
        }
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      unrollRecursionContexts(_parentctx);
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class ListContext extends ParserRuleContext {
    public TerminalNode LIST_START() {
      return getToken(ParadoxFileParser.LIST_START, 0);
    }

    public TerminalNode SYMBOL() {
      return getToken(ParadoxFileParser.SYMBOL, 0);
    }

    public StringContext string() {
      return getRuleContext(StringContext.class, 0);
    }

    public ListContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_list;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof ParadoxFileListener) ((ParadoxFileListener) listener).enterList(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof ParadoxFileListener) ((ParadoxFileListener) listener).exitList(this);
    }
  }

  public final ListContext list() throws RecognitionException {
    ListContext _localctx = new ListContext(_ctx, getState());
    enterRule(_localctx, 32, RULE_list);
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(181);
        match(LIST_START);
        setState(184);
        _errHandler.sync(this);
        switch (_input.LA(1)) {
          case SYMBOL:
            {
              setState(182);
              match(SYMBOL);
            }
            break;
          case SSTRING:
          case DSTRING:
          case CSTRING:
            {
              setState(183);
              string();
            }
            break;
          default:
            throw new NoViableAltException(this);
        }
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  @SuppressWarnings("CheckReturnValue")
  public static class ConstructorContext extends ParserRuleContext {
    public SymbolContext symbol() {
      return getRuleContext(SymbolContext.class, 0);
    }

    public ArrayContext array() {
      return getRuleContext(ArrayContext.class, 0);
    }

    public ConstructorContext(ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex() {
      return RULE_constructor;
    }

    @Override
    public void enterRule(ParseTreeListener listener) {
      if (listener instanceof ParadoxFileListener)
        ((ParadoxFileListener) listener).enterConstructor(this);
    }

    @Override
    public void exitRule(ParseTreeListener listener) {
      if (listener instanceof ParadoxFileListener)
        ((ParadoxFileListener) listener).exitConstructor(this);
    }
  }

  public final ConstructorContext constructor() throws RecognitionException {
    ConstructorContext _localctx = new ConstructorContext(_ctx, getState());
    enterRule(_localctx, 34, RULE_constructor);
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(186);
        symbol();
        setState(187);
        array();
      }
    } catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    } finally {
      exitRule();
    }
    return _localctx;
  }

  public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
    switch (ruleIndex) {
      case 15:
        return expression_sempred((ExpressionContext) _localctx, predIndex);
    }
    return true;
  }

  private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
    switch (predIndex) {
      case 0:
        return precpred(_ctx, 6);
      case 1:
        return precpred(_ctx, 5);
    }
    return true;
  }

  public static final String _serializedATN =
      "\u0004\u0001\u0017\u00be\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001"
          + "\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004"
          + "\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007"
          + "\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b"
          + "\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007"
          + "\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0001\u0000\u0005"
          + "\u0000&\b\u0000\n\u0000\f\u0000)\t\u0000\u0001\u0001\u0001\u0001\u0001"
          + "\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0003"
          + "\u00023\b\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0003"
          + "\u00039\b\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"
          + "\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"
          + "\u0004\u0001\u0004\u0003\u0004G\b\u0004\u0001\u0005\u0001\u0005\u0001"
          + "\u0005\u0001\u0005\u0003\u0005M\b\u0005\u0001\u0006\u0001\u0006\u0001"
          + "\u0007\u0001\u0007\u0001\b\u0001\b\u0001\t\u0001\t\u0001\n\u0001\n\u0001"
          + "\u000b\u0001\u000b\u0005\u000b[\b\u000b\n\u000b\f\u000b^\t\u000b\u0001"
          + "\u000b\u0001\u000b\u0001\f\u0001\f\u0001\f\u0003\fe\b\f\u0001\f\u0001"
          + "\f\u0003\fi\b\f\u0001\f\u0003\fl\b\f\u0005\fn\b\f\n\f\f\fq\t\f\u0001\f"
          + "\u0001\f\u0001\f\u0001\f\u0001\f\u0003\fx\b\f\u0004\fz\b\f\u000b\f\f\f"
          + "{\u0001\f\u0001\f\u0003\f\u0080\b\f\u0001\f\u0001\f\u0003\f\u0084\b\f"
          + "\u0001\f\u0003\f\u0087\b\f\u0005\f\u0089\b\f\n\f\f\f\u008c\t\f\u0001\f"
          + "\u0001\f\u0003\f\u0090\b\f\u0001\r\u0001\r\u0001\r\u0001\r\u0003\r\u0096"
          + "\b\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001"
          + "\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001"
          + "\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0003"
          + "\u000f\u00a9\b\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001"
          + "\u000f\u0001\u000f\u0005\u000f\u00b1\b\u000f\n\u000f\f\u000f\u00b4\t\u000f"
          + "\u0001\u0010\u0001\u0010\u0001\u0010\u0003\u0010\u00b9\b\u0010\u0001\u0011"
          + "\u0001\u0011\u0001\u0011\u0001\u0011\u0000\u0001\u001e\u0012\u0000\u0002"
          + "\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e"
          + " \"\u0000\u0001\u0001\u0000\u000f\u0011\u00d1\u0000\'\u0001\u0000\u0000"
          + "\u0000\u0002*\u0001\u0000\u0000\u0000\u00042\u0001\u0000\u0000\u0000\u0006"
          + "8\u0001\u0000\u0000\u0000\bF\u0001\u0000\u0000\u0000\nL\u0001\u0000\u0000"
          + "\u0000\fN\u0001\u0000\u0000\u0000\u000eP\u0001\u0000\u0000\u0000\u0010"
          + "R\u0001\u0000\u0000\u0000\u0012T\u0001\u0000\u0000\u0000\u0014V\u0001"
          + "\u0000\u0000\u0000\u0016X\u0001\u0000\u0000\u0000\u0018\u008f\u0001\u0000"
          + "\u0000\u0000\u001a\u0095\u0001\u0000\u0000\u0000\u001c\u0097\u0001\u0000"
          + "\u0000\u0000\u001e\u00a8\u0001\u0000\u0000\u0000 \u00b5\u0001\u0000\u0000"
          + "\u0000\"\u00ba\u0001\u0000\u0000\u0000$&\u0003\u0004\u0002\u0000%$\u0001"
          + "\u0000\u0000\u0000&)\u0001\u0000\u0000\u0000\'%\u0001\u0000\u0000\u0000"
          + "\'(\u0001\u0000\u0000\u0000(\u0001\u0001\u0000\u0000\u0000)\'\u0001\u0000"
          + "\u0000\u0000*+\u0003\u0006\u0003\u0000+,\u0005\u0001\u0000\u0000,-\u0003"
          + "\b\u0004\u0000-\u0003\u0001\u0000\u0000\u0000.3\u0003\u0002\u0001\u0000"
          + "/0\u0003\u0006\u0003\u000001\u0003\b\u0004\u000013\u0001\u0000\u0000\u0000"
          + "2.\u0001\u0000\u0000\u00002/\u0001\u0000\u0000\u00003\u0005\u0001\u0000"
          + "\u0000\u000049\u0003\f\u0006\u000059\u0003\n\u0005\u000069\u0003\u001a"
          + "\r\u000079\u0005\n\u0000\u000084\u0001\u0000\u0000\u000085\u0001\u0000"
          + "\u0000\u000086\u0001\u0000\u0000\u000087\u0001\u0000\u0000\u00009\u0007"
          + "\u0001\u0000\u0000\u0000:G\u0003\u000e\u0007\u0000;G\u0003\u0014\n\u0000"
          + "<G\u0003\u0010\b\u0000=G\u0003\u0012\t\u0000>G\u0003\f\u0006\u0000?G\u0003"
          + "\n\u0005\u0000@G\u0003\u001a\r\u0000AG\u0003\u001c\u000e\u0000BG\u0003"
          + "\u0016\u000b\u0000CG\u0003\u0018\f\u0000DG\u0003 \u0010\u0000EG\u0003"
          + "\"\u0011\u0000F:\u0001\u0000\u0000\u0000F;\u0001\u0000\u0000\u0000F<\u0001"
          + "\u0000\u0000\u0000F=\u0001\u0000\u0000\u0000F>\u0001\u0000\u0000\u0000"
          + "F?\u0001\u0000\u0000\u0000F@\u0001\u0000\u0000\u0000FA\u0001\u0000\u0000"
          + "\u0000FB\u0001\u0000\u0000\u0000FC\u0001\u0000\u0000\u0000FD\u0001\u0000"
          + "\u0000\u0000FE\u0001\u0000\u0000\u0000G\t\u0001\u0000\u0000\u0000HM\u0003"
          + "\f\u0006\u0000IM\u0005\u000b\u0000\u0000JM\u0005\r\u0000\u0000KM\u0005"
          + "\u0012\u0000\u0000LH\u0001\u0000\u0000\u0000LI\u0001\u0000\u0000\u0000"
          + "LJ\u0001\u0000\u0000\u0000LK\u0001\u0000\u0000\u0000M\u000b\u0001\u0000"
          + "\u0000\u0000NO\u0007\u0000\u0000\u0000O\r\u0001\u0000\u0000\u0000PQ\u0005"
          + "\u000b\u0000\u0000Q\u000f\u0001\u0000\u0000\u0000RS\u0005\r\u0000\u0000"
          + "S\u0011\u0001\u0000\u0000\u0000TU\u0005\u000e\u0000\u0000U\u0013\u0001"
          + "\u0000\u0000\u0000VW\u0005\f\u0000\u0000W\u0015\u0001\u0000\u0000\u0000"
          + "X\\\u0005\u0002\u0000\u0000Y[\u0003\u0002\u0001\u0000ZY\u0001\u0000\u0000"
          + "\u0000[^\u0001\u0000\u0000\u0000\\Z\u0001\u0000\u0000\u0000\\]\u0001\u0000"
          + "\u0000\u0000]_\u0001\u0000\u0000\u0000^\\\u0001\u0000\u0000\u0000_`\u0005"
          + "\u0003\u0000\u0000`\u0017\u0001\u0000\u0000\u0000ab\u0005\u0002\u0000"
          + "\u0000bd\u0003\b\u0004\u0000ce\u0005\u0015\u0000\u0000dc\u0001\u0000\u0000"
          + "\u0000de\u0001\u0000\u0000\u0000eo\u0001\u0000\u0000\u0000fi\u0003\b\u0004"
          + "\u0000gi\u0003\u0002\u0001\u0000hf\u0001\u0000\u0000\u0000hg\u0001\u0000"
          + "\u0000\u0000ik\u0001\u0000\u0000\u0000jl\u0005\u0015\u0000\u0000kj\u0001"
          + "\u0000\u0000\u0000kl\u0001\u0000\u0000\u0000ln\u0001\u0000\u0000\u0000"
          + "mh\u0001\u0000\u0000\u0000nq\u0001\u0000\u0000\u0000om\u0001\u0000\u0000"
          + "\u0000op\u0001\u0000\u0000\u0000pr\u0001\u0000\u0000\u0000qo\u0001\u0000"
          + "\u0000\u0000rs\u0005\u0003\u0000\u0000s\u0090\u0001\u0000\u0000\u0000"
          + "ty\u0005\u0002\u0000\u0000uw\u0003\u0002\u0001\u0000vx\u0005\u0015\u0000"
          + "\u0000wv\u0001\u0000\u0000\u0000wx\u0001\u0000\u0000\u0000xz\u0001\u0000"
          + "\u0000\u0000yu\u0001\u0000\u0000\u0000z{\u0001\u0000\u0000\u0000{y\u0001"
          + "\u0000\u0000\u0000{|\u0001\u0000\u0000\u0000|}\u0001\u0000\u0000\u0000"
          + "}\u007f\u0003\b\u0004\u0000~\u0080\u0005\u0015\u0000\u0000\u007f~\u0001"
          + "\u0000\u0000\u0000\u007f\u0080\u0001\u0000\u0000\u0000\u0080\u008a\u0001"
          + "\u0000\u0000\u0000\u0081\u0084\u0003\b\u0004\u0000\u0082\u0084\u0003\u0002"
          + "\u0001\u0000\u0083\u0081\u0001\u0000\u0000\u0000\u0083\u0082\u0001\u0000"
          + "\u0000\u0000\u0084\u0086\u0001\u0000\u0000\u0000\u0085\u0087\u0005\u0015"
          + "\u0000\u0000\u0086\u0085\u0001\u0000\u0000\u0000\u0086\u0087\u0001\u0000"
          + "\u0000\u0000\u0087\u0089\u0001\u0000\u0000\u0000\u0088\u0083\u0001\u0000"
          + "\u0000\u0000\u0089\u008c\u0001\u0000\u0000\u0000\u008a\u0088\u0001\u0000"
          + "\u0000\u0000\u008a\u008b\u0001\u0000\u0000\u0000\u008b\u008d\u0001\u0000"
          + "\u0000\u0000\u008c\u008a\u0001\u0000\u0000\u0000\u008d\u008e\u0005\u0003"
          + "\u0000\u0000\u008e\u0090\u0001\u0000\u0000\u0000\u008fa\u0001\u0000\u0000"
          + "\u0000\u008ft\u0001\u0000\u0000\u0000\u0090\u0019\u0001\u0000\u0000\u0000"
          + "\u0091\u0092\u0005\u0004\u0000\u0000\u0092\u0096\u0005\u0012\u0000\u0000"
          + "\u0093\u0094\u0005\u0004\u0000\u0000\u0094\u0096\u0005\u000b\u0000\u0000"
          + "\u0095\u0091\u0001\u0000\u0000\u0000\u0095\u0093\u0001\u0000\u0000\u0000"
          + "\u0096\u001b\u0001\u0000\u0000\u0000\u0097\u0098\u0005\u0004\u0000\u0000"
          + "\u0098\u0099\u0005\u0005\u0000\u0000\u0099\u009a\u0003\u001e\u000f\u0000"
          + "\u009a\u009b\u0005\u0006\u0000\u0000\u009b\u001d\u0001\u0000\u0000\u0000"
          + "\u009c\u009d\u0006\u000f\uffff\uffff\u0000\u009d\u009e\u0005\u0007\u0000"
          + "\u0000\u009e\u009f\u0003\u001e\u000f\u0000\u009f\u00a0\u0005\u0007\u0000"
          + "\u0000\u00a0\u00a9\u0001\u0000\u0000\u0000\u00a1\u00a2\u0005\b\u0000\u0000"
          + "\u00a2\u00a3\u0003\u001e\u000f\u0000\u00a3\u00a4\u0005\t\u0000\u0000\u00a4"
          + "\u00a9\u0001\u0000\u0000\u0000\u00a5\u00a6\u0005\u0013\u0000\u0000\u00a6"
          + "\u00a9\u0003\b\u0004\u0000\u00a7\u00a9\u0003\b\u0004\u0000\u00a8\u009c"
          + "\u0001\u0000\u0000\u0000\u00a8\u00a1\u0001\u0000\u0000\u0000\u00a8\u00a5"
          + "\u0001\u0000\u0000\u0000\u00a8\u00a7\u0001\u0000\u0000\u0000\u00a9\u00b2"
          + "\u0001\u0000\u0000\u0000\u00aa\u00ab\n\u0006\u0000\u0000\u00ab\u00ac\u0005"
          + "\u0014\u0000\u0000\u00ac\u00b1\u0003\u001e\u000f\u0007\u00ad\u00ae\n\u0005"
          + "\u0000\u0000\u00ae\u00af\u0005\u0013\u0000\u0000\u00af\u00b1\u0003\u001e"
          + "\u000f\u0006\u00b0\u00aa\u0001\u0000\u0000\u0000\u00b0\u00ad\u0001\u0000"
          + "\u0000\u0000\u00b1\u00b4\u0001\u0000\u0000\u0000\u00b2\u00b0\u0001\u0000"
          + "\u0000\u0000\u00b2\u00b3\u0001\u0000\u0000\u0000\u00b3\u001f\u0001\u0000"
          + "\u0000\u0000\u00b4\u00b2\u0001\u0000\u0000\u0000\u00b5\u00b8\u0005\n\u0000"
          + "\u0000\u00b6\u00b9\u0005\u0012\u0000\u0000\u00b7\u00b9\u0003\f\u0006\u0000"
          + "\u00b8\u00b6\u0001\u0000\u0000\u0000\u00b8\u00b7\u0001\u0000\u0000\u0000"
          + "\u00b9!\u0001\u0000\u0000\u0000\u00ba\u00bb\u0003\n\u0005\u0000\u00bb"
          + "\u00bc\u0003\u0018\f\u0000\u00bc#\u0001\u0000\u0000\u0000\u0016\'28FL"
          + "\\dhkow{\u007f\u0083\u0086\u008a\u008f\u0095\u00a8\u00b0\u00b2\u00b8";
  public static final ATN _ATN = new ATNDeserializer().deserialize(_serializedATN.toCharArray());

  static {
    _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
    for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
      _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
    }
  }
}
