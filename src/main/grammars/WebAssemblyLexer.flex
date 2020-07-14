package org.jetbrains.webstorm.lang.lexer;

import com.intellij.lexer.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.TokenType;
import org.jetbrains.webstorm.lang.psi.WebAssemblyTypes;

%%

%{
  public _WebAssemblyLexer() {
    this((java.io.Reader)null);
  }
%}

%class _WebAssemblyLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%unicode

%state BLOCKCHAR

SPACE = [\s\t\n\r]
LINECOMMENT = ;; .*

// integers
SIGN = [+-]
DIGIT = [0-9]
HEXDIGIT = {DIGIT} | [a-fA-F]
NUM = {DIGIT} (_? {DIGIT})*
HEXNUM = {HEXDIGIT} (_? {HEXDIGIT})*

UN = {NUM} | 0x{HEXNUM}
SN = {SIGN} ({NUM} | 0x {HEXNUM})

// floating-point
FRAC = {NUM}
HEXFRAC = {HEXNUM}
FLOAT = {NUM} (\. {FRAC}?)? ([Ee] {SIGN}? {NUM})?
HEXFLOAT = 0x {HEXNUM} (\. {HEXFRAC}?)? ([Pp] {SIGN}? {NUM})?

FN = {SIGN}? ({FLOAT} | {HEXFLOAT} | inf | nan | nan:0x {HEXNUM})

// string
STRING = \" {STRINGELEM}* \"
STRINGELEM = \\ {HEXDIGIT} {HEXDIGIT} | \\u\{ {HEXNUM} } | [ !#-\[\]-~] | \\t | \\n | \\r | \\\" | \\′ | \\

// identifiers
ID = \$ ({DIGIT} | [A-Za-z!#$%&′*+\-./:<=>?@\\\^_`|~])+

// types
VALTYPE = [if] (32 | 64)

// instructions
ONEWORDINSTR = "unreachable" | "nop" | "return"    // control
             | "drop" | "select"                   // parametric
             | "memory.size" | "memory.grow"       // memory
             // numeric
             | i (32 | 64) \. (c[lt]z | popcnt | add | sub | mul | (div | rem | shr) _ [su] | and | x?or | shl
                                      | rot[lr] | eqz? | ne | [lg][te] _[su])
             | f (32 | 64) \. (abs | neg | ceil | floor | trunc | nearest | sqrt | add | sub | mul | div | min | max
                                   | copysign | eq | ne | [lg][te])
             | i32 \. wrap_i64
             | i (32 | 64) \. trunc_ ((f | sat) (32 | 64) _ [su])
             | i64 \. extend_i32[su]
             | f (32 | 64) \. convert_ (i (32 | 64) _ [su])
             | f32 \. demote_f64 | f64 \. promote_f32
             | i32 \. reinterpret_f32 | i64 \. reinterpret_f64 | f32 \. reinterpret_i32 | f64 \. reinterpret_i64
             | i32 \. extend (8 | 16) _s | i64 \. extend (8 | 16 | 32) _s
VARIABLEINSTR = ("local" | "global") \. ("get" | "set") | "local.tee"
MEMORYINSTR = {VALTYPE} \. ("load" | "store")
            | "i32.load"  (8 | 16)_[su] | "i64.load"  (8 | 16 | 32)_[su]
            | "i32.store" (8 | 16) | "i64.store" (8 | 16 | 32)

%%
<YYINITIAL> {
    {VALTYPE}                                       { return WebAssemblyTypes.VALTYPE; }
    {ONEWORDINSTR}                                  { return WebAssemblyTypes.ONEWORDINSTR; }
    {VARIABLEINSTR}                                 { return WebAssemblyTypes.VARIABLEINSTR; }
    {MEMORYINSTR}                                   { return WebAssemblyTypes.MEMORYINSTR; }

    "func"                                          { return WebAssemblyTypes.FUNCKEY; }
    "param"                                         { return WebAssemblyTypes.PARAMKEY; }
    "result"                                        { return WebAssemblyTypes.RESULTKEY; }
    "funcref"                                       { return WebAssemblyTypes.FUNCREFKEY; }
    "mut"                                           { return WebAssemblyTypes.MUTKEY; }

    "block"                                         { return WebAssemblyTypes.BLOCKKEY; }
    "loop"                                          { return WebAssemblyTypes.LOOPKEY; }
    "end"                                           { return WebAssemblyTypes.ENDKEY; }
    "if"                                            { return WebAssemblyTypes.IFKEY; }
    "else"                                          { return WebAssemblyTypes.ELSEKEY; }
    "br"                                            { return WebAssemblyTypes.BRKEY; }
    "br_if"                                         { return WebAssemblyTypes.BRIFKEY; }
    "call"                                          { return WebAssemblyTypes.CALLKEY; }
    "br_table"                                      { return WebAssemblyTypes.BRTABLEKEY; }
    "call_indirect"                                 { return WebAssemblyTypes.CALLINDIRECTKEY; }
    "offset="                                       { return WebAssemblyTypes.OFFSETEQKEY; }
    "align="                                        { return WebAssemblyTypes.ALIGNEQKEY; }
    i (32 | 64) \.const                             { return WebAssemblyTypes.ICONST; }
    f (32 | 64) \.const                             { return WebAssemblyTypes.FCONST; }

    "type"                                          { return WebAssemblyTypes.TYPEKEY; }
    "import"                                        { return WebAssemblyTypes.IMPORTKEY; }
    "table"                                         { return WebAssemblyTypes.TABLEKEY; }
    "memory"                                        { return WebAssemblyTypes.MEMORYKEY; }
    "global"                                        { return WebAssemblyTypes.GLOBALKEY; }
    "local"                                         { return WebAssemblyTypes.LOCALKEY; }
    "export"                                        { return WebAssemblyTypes.EXPORTKEY; }
    "elem"                                          { return WebAssemblyTypes.ELEMKEY; }
    "data"                                          { return WebAssemblyTypes.DATAKEY; }
    "offset"                                        { return WebAssemblyTypes.OFFSETKEY; }
    "start"                                         { return WebAssemblyTypes.STARTKEY; }
    "module"                                        { return WebAssemblyTypes.MODULEKEY; }
    "global"                                        { return WebAssemblyTypes.GLOBALKEY; }

    "("                                             { return WebAssemblyTypes.LEFT_BRACKET; }
    ")"                                             { return WebAssemblyTypes.RIGHT_BRACKET; }

    {UN}                                            { return WebAssemblyTypes.UNSIGNED; }
    {SN}                                            { return WebAssemblyTypes.SIGNED; }
    {FN}                                            { return WebAssemblyTypes.FLOAT; }
    {STRING}                                        { return WebAssemblyTypes.STRING; }

    {ID}                                            { return WebAssemblyTypes.IDENTIFIER; }

    {SPACE}+                                        { return TokenType.WHITE_SPACE; }
    {LINECOMMENT}                                   { return WebAssemblyTypes.LINE_COMMENT; }
    "(;"                                            { yybegin(BLOCKCHAR); return WebAssemblyTypes.COMMENT_LEFT_BR; }
    ";)"                                            { yybegin(YYINITIAL); return WebAssemblyTypes.COMMENT_RIGHT_BR; }

    [^]                                             { return TokenType.BAD_CHARACTER; }
}

<BLOCKCHAR> {
    [^;(]                                           { yybegin(BLOCKCHAR); return WebAssemblyTypes.COMMENT_CHAR; }
    ";"[^)]                                         { yybegin(BLOCKCHAR); yypushback(1); return WebAssemblyTypes.COMMENT_CHAR; }
    "("[^;]                                         { yybegin(BLOCKCHAR); yypushback(1); return WebAssemblyTypes.COMMENT_CHAR; }
    [^]                                             { yybegin(YYINITIAL); yypushback(yylength()); }
}
