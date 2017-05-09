/*
 * JFlex specification for the lexical analyzer for a simple demo language.
 * Change this into the scanner for your implementation of MiniJava.
 * CSE 401/P501 Au11
 */


package Scanner;

import java_cup.runtime.*;
import Parser.sym;

%%

%public
%final
%class scanner
%unicode
%cup
%line
%column

/* Code copied into the generated scanner class.  */
/* Can be referenced in scanner action code. */
%{
  // Return new symbol objects with line and column numbers in the symbol 
  // left and right fields. This abuses the original idea of having left 
  // and right be character positions, but is   // is more useful and 
  // follows an example in the JFlex documentation.
  private Symbol symbol(int type) {
    return new Symbol(type, yyline+1, yycolumn+1);
  }
  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline+1, yycolumn+1, value);
  }

  // Return a readable representation of symbol s (aka token)
  public String symbolToString(Symbol s) {
    String rep;
    switch (s.sym) {
      case sym.BECOMES: return "BECOMES";
      case sym.SEMICOLON: return "SEMICOLON";
      case sym.PLUS: return "PLUS";
      case sym.LPAREN: return "LPAREN";
      case sym.RPAREN: return "RPAREN";
      case sym.DISPLAY: return "DISPLAY";
      case sym.PUBLIC: return "PUBLIC";
      case sym.CLASS: return "CLASS";
      case sym.EXTENDS: return "EXTENDS";
      case sym.STATIC: return "STATIC";
      case sym.VOID: return "VOID";
      case sym.MAIN: return "MAIN";
      case sym.PRINT: return "PRINT";
      case sym.COLON: return "COLON";
      case sym.RETURN: return "RETURN";
      case sym.IF: return "IF";
      case sym.ELSE: return "ELSE";
      case sym.WHILE: return "WHILE";
      case sym.DOT: return "DOT";
      case sym.LENGTH: return "LENGTH";
      case sym.THIS: return "THIS";
      case sym.NEW: return "NEW";
      
      case sym.MINUS: return "MINUS";
      case sym.TIMES: return "TIMES";
      case sym.AND: return "AND";
      
      case sym.INT: return "INT_TYPE";
      case sym.STRING_TYPE: return "STRING_TYPE";
      
      case sym.TRUE: return "TRUE";
      case sym.FALSE: return "FALSE";
      case sym.LESSTHAN: return "LESSTHAN";
      case sym.NOT: return "NOT";
      
      case sym.LBRACE: return "LBRACE";
      case sym.RBRACE: return "RBRACE";
      case sym.LBRACKET: return "LBRACKET";
      case sym.RBRACKET: return "RBRACKET";

      case sym.STRING: return "STRING(" + (String)s.value + ")";
      case sym.NUMBER: return "NUMBER(" + (String)s.value + ")";
      case sym.IDENTIFIER: return "ID(" + (String)s.value + ")";
      case sym.EOF: return "<EOF>";
      case sym.error: return "<ERROR>";
      default: return "<UNEXPECTED TOKEN " + s.toString() + ">";
    }
  }
%}

/* Helper definitions */
letter = [a-zA-Z]
digit = [0-9]
eol = [\r\n]
white = {eol}|[ \t]
LineComment	= "//".*
GeneralComment	= "/*"([^*]|\*+[^*/])*\*+"/"
comment = {GeneralComment} | {LineComment}

%%

/* Token definitions */

/* reserved words */
/* (put here so that reserved words take precedence over identifiers) */
"display" { return symbol(sym.DISPLAY); }
"public" { return symbol(sym.PUBLIC); }
"class" { return symbol(sym.CLASS); }
"extends" { return symbol(sym.EXTENDS); }
"static" { return symbol(sym.STATIC); }
"void" { return symbol(sym.VOID); }
"main" { return symbol(sym.MAIN); }
"System.out.println" { return symbol(sym.PRINT); }
"return" { return symbol(sym.RETURN); }
"if" { return symbol(sym.IF); }
"else" { return symbol(sym.ELSE); }
"while" { return symbol(sym.WHILE); }
"true" { return symbol(sym.TRUE); }
"false" { return symbol(sym.FALSE); }
"." { return symbol(sym.DOT); }
"length" { return symbol(sym.LENGTH); }
"this" { return symbol(sym.THIS); }
"new" { return symbol(sym.NEW); }

/* primitive type */
"int" { return symbol(sym.INT); }
"boolean" { return symbol(sym.BOOLEAN); }
"String" { return symbol(sym.STRING_TYPE); }

/* operators */
"+" { return symbol(sym.PLUS); }
"=" { return symbol(sym.BECOMES); }
"-" { return symbol(sym.MINUS); }
"*" { return symbol(sym.TIMES); }
"&&" { return symbol(sym.AND); }
"<" { return symbol(sym.LESSTHAN); }
"!" { return symbol(sym.NOT); }

/* delimiters */
"(" { return symbol(sym.LPAREN); }
")" { return symbol(sym.RPAREN); }
";" { return symbol(sym.SEMICOLON); }
"," { return symbol(sym.COLON); }
"{" { return symbol(sym.LBRACE); }
"}" { return symbol(sym.RBRACE); }
"[" { return symbol(sym.LBRACKET); }
"]" { return symbol(sym.RBRACKET); }

/* identifiers */
{letter} ({letter}|{digit}|_)* { return symbol(sym.IDENTIFIER, yytext()); }

/* string */
"\"".*"\"" { return symbol(sym.STRING, yytext()); }
{digit}+ { return symbol(sym.NUMBER, yytext()); }

/* whitespace */
{white}+ { /* ignore whitespace */ }

/* comments */
{comment}+ { /* ignore comments */ }


/* lexical errors (put last so other matches take precedence) */
. { System.err.println(
	"\nunexpected character in input: '" + yytext() + "' at line " +
	(yyline+1) + " column " + (yycolumn+1));
  }
