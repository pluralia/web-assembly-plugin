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
LINECOMMENT = ;;.*

// integers
SIGN = [+-]
DIGIT = [0-9]
HEXDIGIT = {DIGIT} | [a-fA-F]
NUM = {DIGIT} (_? {DIGIT})*
HEXNUM = {HEXDIGIT} (_? {HEXDIGIT})*

UN = {NUM} | 0x {HEXNUM}
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
// control
CONTROLINSTR = unreachable | nop | return
CONTROLINSTR_IDX = br(_if)? | call
BRTABLEINSTR = br_table
CALLINDIRECTINSTR = call_indirect

// parametric
PARAMETRICINSTR = drop | select

// variable
VARIABLEINSTR_IDX = local\.([gs]et | tee)
                  | global\.[gs]et

// memory
MEMORYINSTR = memory\.(size | grow)
MEMORYINSTR_MEMARG = {VALTYPE}\.(load | store)
                   | i32\.(load((8 | 16)_[su]) | store(8 | 16))
                   | i64\.(load((8 | 16 | 32)_[su]) | store(8 | 16 | 32))

// numetic
ICONST = i(32 | 64)\.const
FCONST = i(32 | 64)\.const
NUMERICINSTR = {VALTYPE}\.const
             | i(32 | 64)\.(c[lt]z | popcnt | add | sub | mul | (div | rem | shr)_[su] | and | x?or | shl | rot[lr]
                                   | eqz? | ne | [lg][te]_[su] | trunc_((f | sat)(32 | 64)_[su]))
             | i32\.(wrap_i64 | extend(8 | 16)_s)
             | i64\.(extend_i32[su] | extend(8 | 16 | 32)_s)
             | f (32 | 64)\.(abs | neg | ceil | floor | trunc | nearest | sqrt | add | sub | mul | div | min | max
                                 | copysign | eq | ne | [lg][te]] | convert_i(32 | 64)_[su])
             | f32\.demote_f64 | f64\.promote_f32
             | i32\.reinterpret_f32 | i64\.reinterpret_f64 | f32\.reinterpret_i32 | f64\.reinterpret_i64

%%
<YYINITIAL> {
    {VALTYPE}                                       { return WebAssemblyTypes.VALTYPE; }

    {CONTROLINSTR}                                  { return WebAssemblyTypes.CONTROLINSTR; }
    {CONTROLINSTR_IDX}                              { return WebAssemblyTypes.CONTROLINSTR_IDX; }
    {BRTABLEINSTR}                                  { return WebAssemblyTypes.BRTABLEINSTR; }
    {CALLINDIRECTINSTR}                             { return WebAssemblyTypes.CALLINDIRECTINSTR; }

    {PARAMETRICINSTR}                               { return WebAssemblyTypes.PARAMETRICINSTR; }

    {VARIABLEINSTR_IDX}                             { return WebAssemblyTypes.VARIABLEINSTR_IDX; }

    {MEMORYINSTR}                                  { return WebAssemblyTypes.MEMORYINSTR; }
    {MEMORYINSTR_MEMARG}                           { return WebAssemblyTypes.MEMORYINSTR_MEMARG; }

    {ICONST}                                        { return WebAssemblyTypes.ICONST; }
    {FCONST}                                        { return WebAssemblyTypes.FCONST; }
    {NUMERICINSTR}                                  { return WebAssemblyTypes.NUMERICINSTR; }

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
    "offset="                                       { return WebAssemblyTypes.OFFSETEQKEY; }
    "align="                                        { return WebAssemblyTypes.ALIGNEQKEY; }

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

    {ID}                                            { return WebAssemblyTypes.IDENTIFIER; }
    {STRING}                                        { return WebAssemblyTypes.STRING; }
    {UN}                                            { return WebAssemblyTypes.UNSIGNED; }
    {SN}                                            { return WebAssemblyTypes.SIGNED; }
    {FN}                                            { return WebAssemblyTypes.FLOAT; }

    "("                                             { return WebAssemblyTypes.LPAR; }
    ")"                                             { return WebAssemblyTypes.RPAR; }

    {SPACE}+                                        { return TokenType.WHITE_SPACE; }

    {LINECOMMENT}                                   { return WebAssemblyTypes.LINE_COMMENT; }
    "(;"                                            { yybegin(BLOCKCHAR); return WebAssemblyTypes.BLOCK_COMMENT_START; }
    ";)"                                            { yybegin(YYINITIAL); return WebAssemblyTypes.BLOCK_COMMENT_FINISH; }

    [^]                                             { return TokenType.BAD_CHARACTER; }
}

<BLOCKCHAR> {
    [^;(]                                           { yybegin(BLOCKCHAR); return WebAssemblyTypes.BLOCK_COMMENT_CHAR; }
    ";"[^)]                                         { yybegin(BLOCKCHAR); yypushback(1); return WebAssemblyTypes.BLOCK_COMMENT_CHAR; }
    "("[^;]                                         { yybegin(BLOCKCHAR); yypushback(1); return WebAssemblyTypes.BLOCK_COMMENT_CHAR; }
    [^]                                             { yybegin(YYINITIAL); yypushback(yylength()); }
}
