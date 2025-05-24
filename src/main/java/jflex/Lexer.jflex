package jflex;

import java_cup.runtime.Symbol;
import cup.ParserSym;
import errors.TokenError;
import errors.ErrorHandler;
import tokens.TokenHandler;
import tokens.Token;

%%

%class Lexer
%cup
%public
%line
%column
%full
%char

%{
    private TokenHandler tokenHandler = TokenHandler.getInstance();

    private Symbol addToken(int type, String value) {
        Token token = new Token(
            type,
            ParserSym.terminalNames[type],
            value,
            yyline + 1,
            yycolumn + 1
        );
        tokenHandler.addToken(token);
        return new Symbol(type, yyline, yycolumn, value);
    }

    private ErrorHandler errorHandler = ErrorHandler.getInstance();

    private void addError(String type, String lexeme, String message) {
        TokenError error = new TokenError(
            "Lexico",
            yyline + 1,
            yycolumn + 1,
            lexeme,
            message,
            "Lexical analysis error",
            "Lexer.jflex"
        );
        errorHandler.addError(error);
    }
%}

%init{
    yyline = 0;
    yycolumn = 0;
%init}

whitespace = [ \t\f\r]+
newline    = \n
digit      = [0-9]
digits     = {digit}+
letter     = [a-zA-Z]
id_rest    = ({letter}|{digit}|[_])*

float = {digits}"."{digits}

line_comment     = "//".*
block_comment    = "/*"([^*]|\*+[^/])*"\*/"

%%

{whitespace}      {}

{newline}         {}

{line_comment}    {}
{block_comment}   {}

"strategy"        { return addToken(ParserSym.STRATEGY, yytext()); }
"match"           { return addToken(ParserSym.MATCH, yytext()); }
"main"            { return addToken(ParserSym.MAIN, yytext()); }
"initial"         { return addToken(ParserSym.INITIAL, yytext()); }
"rules"           { return addToken(ParserSym.RULES, yytext()); }
"if"              { return addToken(ParserSym.IF, yytext()); }
"then"            { return addToken(ParserSym.THEN, yytext()); }
"else"            { return addToken(ParserSym.ELSE, yytext()); }
"scoring"         { return addToken(ParserSym.SCORING, yytext()); }
"run"             { return addToken(ParserSym.RUN, yytext()); }
"with"            { return addToken(ParserSym.WITH, yytext()); }
"seed"            { return addToken(ParserSym.SEED, yytext()); }
"players"         { return addToken(ParserSym.PLAYERS, yytext()); }
"strategies"      { return addToken(ParserSym.STRATEGIES, yytext()); }
"rounds"          { return addToken(ParserSym.ROUNDS, yytext()); }

"mutual"          { return addToken(ParserSym.MUTUAL, yytext()); }
"cooperation"     { return addToken(ParserSym.COOPERATION, yytext()); }
"defection"       { return addToken(ParserSym.DEFECTION, yytext()); }
"betrayal"        { return addToken(ParserSym.BETRAYAL, yytext()); }
"reward"          { return addToken(ParserSym.REWARD, yytext()); }
"punishment"      { return addToken(ParserSym.PUNISHMENT, yytext()); }

"round_number"    { return addToken(ParserSym.ROUND_NUMBER, yytext()); }
"opponent_history" { return addToken(ParserSym.OPPONENT_HISTORY, yytext()); }
"self_history"    { return addToken(ParserSym.SELF_HISTORY, yytext()); }
"total_rounds"    { return addToken(ParserSym.TOTAL_ROUNDS, yytext()); }
"random"          { return addToken(ParserSym.RANDOM, yytext()); }

"get_move"        { return addToken(ParserSym.GET_MOVE, yytext()); }
"last_move"       { return addToken(ParserSym.LAST_MOVE, yytext()); }
"get_moves_count" { return addToken(ParserSym.GET_MOVES_COUNT, yytext()); }
"get_last_n_moves" { return addToken(ParserSym.GET_LAST_N_MOVES, yytext()); }

"C"               { return addToken(ParserSym.C, yytext()); }
"D"               { return addToken(ParserSym.D, yytext()); }

"true"            { return addToken(ParserSym.TRUE, yytext()); }
"false"           { return addToken(ParserSym.FALSE, yytext()); }

"&&"              { return addToken(ParserSym.AND, yytext()); }
"||"              { return addToken(ParserSym.OR, yytext()); }
"!"               { return addToken(ParserSym.NOT, yytext()); }
"=="              { return addToken(ParserSym.EQ, yytext()); }
"!="              { return addToken(ParserSym.NEQ, yytext()); }
">="              { return addToken(ParserSym.GE, yytext()); }
"<="              { return addToken(ParserSym.LE, yytext()); }
">"               { return addToken(ParserSym.GT, yytext()); }
"<"               { return addToken(ParserSym.LT, yytext()); }

"{"               { return addToken(ParserSym.LBRACE, yytext()); }
"}"               { return addToken(ParserSym.RBRACE, yytext()); }
"("               { return addToken(ParserSym.LPAREN, yytext()); }
")"               { return addToken(ParserSym.RPAREN, yytext()); }
"["               { return addToken(ParserSym.LBRACKET, yytext()); }
"]"               { return addToken(ParserSym.RBRACKET, yytext()); }
":"               { return addToken(ParserSym.COLON, yytext()); }
","               { return addToken(ParserSym.COMMA, yytext()); }

{float}          { return addToken(ParserSym.FLOATLIT, yytext()); }
{digits}         { return addToken(ParserSym.INTLIT, yytext()); }

{letter}{id_rest} { return addToken(ParserSym.ID, yytext()); }

<<EOF>>         { return addToken(ParserSym.EOF, "EOF"); }

. {
    addError("Lexical", yytext(), "Unrecognized character");
    return addToken(ParserSym.error, yytext());
}
