package org.jetbrains.webstorm.lang.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.TokenType;
import static org.jetbrains.webstorm.lang.psi.WebAssemblyTypes.*;

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

%{
    private int blockCommentStart;
    private int blockCommentDepth;
%}

%state BLOCKCOMMENTST

SPACE = [\s\t\n\r]

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
VARIABLEINSTR_IDX = local\.([gs]et | tee) | global\.[gs]et
                  // WebAssembly v1.0
                  | [gs]et_(local | global) | tee_local

// memory
MEMORYINSTR = memory\.(size | grow)
            // WebAssembly v1.0
            | (current | grow)_memory
MEMORYINSTR_MEMARG = {VALTYPE}\.(load | store)
                   | i32\.(load((8 | 16)_[su]) | store(8 | 16))
                   | i64\.(load((8 | 16 | 32)_[su]) | store(8 | 16 | 32))
                   // WebAssembly v1.0
                   | i32\.atomic\.(wake | load((8 | 16)_[su])?
                                 | store(8 | 16)? | rmw((8 | 16)_u)?\.(add | sub | and | x?or | (cmp)?xchg))
                   | i32\.atomic\.(wake | load((8 | 16 | 32)_[su])?
                                 | store(8 | 16 | 32)? | rmw((8 | 16 | 32)_u)?\.(add | sub | and | x?or | (cmp)?xchg))

// numeric
ICONST = i(32 | 64)\.const
FCONST = f(32 | 64)\.const
NUMERICINSTR = {VALTYPE}\.const
             | i(32 | 64)\.(c[lt]z | popcnt | add | sub | mul | (div | rem | shr)_[su] | and | x?or | shl | rot[lr]
                                   | eqz? | ne | [lg][te]_[su] | trunc_((f | sat)(32 | 64)_[su]))
             | i32\.(wrap_i64 | extend(8 | 16)_s)
             | i64\.(extend_i32[su] | extend(8 | 16 | 32)_s)
             | f (32 | 64)\.(abs | neg | ceil | floor | trunc | nearest | sqrt | add | sub | mul | div | min | max
                                 | copysign | eq | ne | [lg][te]] | convert_i(32 | 64)_[su])
             | f32\.demote_f64 | f64\.promote_f32
             | i32\.reinterpret_f32 | i64\.reinterpret_f64 | f32\.reinterpret_i32 | f64\.reinterpret_i64
             // WebAssembly v1.0
             | i32\.wrap\/i64
             | i(32 | 64)\.(trunc_[su]\/f | convert[su]\/i)(32 | 64)
             | i64\.extend_[su]\/i32
             | f32\.demote\/f64 | f64\.promote\/f32
             | i32\.reinterpret\/f32 | i64\.reinterpret\/f64 | f32\.reinterpret\/i32 | f64\.reinterpret\/i64

FUNCREF = "funcref"
        // WebAssembly v1.0
        | "anyfunc"

// comments
LINECOMMENT = ;;.*
BLOCKCOMMENTSTART = "(;"
BLOCKCOMMENTEND = ";)"

%%

<BLOCKCOMMENTST> {
    {BLOCKCOMMENTSTART} {
        blockCommentDepth++;
    }

    <<EOF>> {
        yybegin(YYINITIAL);
        zzStartRead = blockCommentStart;
        return BLOCK_COMMENT;
    }

    {BLOCKCOMMENTEND} {
        if (blockCommentDepth > 0) {
            blockCommentDepth--;
        } else {
            yybegin(YYINITIAL);
            zzStartRead = blockCommentStart;
            return BLOCK_COMMENT;
        }
    }

    [^] {}
}

<YYINITIAL> {
    {VALTYPE}                   { return VALTYPE; }

    {CONTROLINSTR}              { return CONTROLINSTR; }
    {CONTROLINSTR_IDX}          { return CONTROLINSTR_IDX; }
    {BRTABLEINSTR}              { return BRTABLEINSTR; }
    {CALLINDIRECTINSTR}         { return CALLINDIRECTINSTR; }

    {PARAMETRICINSTR}           { return PARAMETRICINSTR; }

    {VARIABLEINSTR_IDX}         { return VARIABLEINSTR_IDX; }

    {MEMORYINSTR}               { return MEMORYINSTR; }
    {MEMORYINSTR_MEMARG}        { return MEMORYINSTR_MEMARG; }

    {FCONST}                    { return FCONST; }
    {ICONST}                    { return ICONST; }
    {NUMERICINSTR}              { return NUMERICINSTR; }

    {FUNCREF}                   { return FUNCREFKEY; }
      
    "func"                      { return FUNCKEY; }
    "param"                     { return PARAMKEY; }
    "result"                    { return RESULTKEY; }
    "mut"                       { return MUTKEY; }

    "block"                     { return BLOCKKEY; }
    "loop"                      { return LOOPKEY; }
    "end"                       { return ENDKEY; }
    "if"                        { return IFKEY; }
    "then"                      { return THENKEY; }
    "else"                      { return ELSEKEY; }
    "offset="                   { return OFFSETEQKEY; }
    "align="                    { return ALIGNEQKEY; }

    "type"                      { return TYPEKEY; }
    "import"                    { return IMPORTKEY; }
    "table"                     { return TABLEKEY; }
    "memory"                    { return MEMORYKEY; }
    "global"                    { return GLOBALKEY; }
    "local"                     { return LOCALKEY; }
    "export"                    { return EXPORTKEY; }
    "elem"                      { return ELEMKEY; }
    "data"                      { return DATAKEY; }
    "offset"                    { return OFFSETKEY; }
    "start"                     { return STARTKEY; }
    "module"                    { return MODULEKEY; }
    "global"                    { return GLOBALKEY; }

    {ID}                        { return IDENTIFIER; }
    {STRING}                    { return STRING; }
    {UN}                        { return UNSIGNED; }
    {SN}                        { return SIGNED; }
    {FN}                        { return FLOAT; }

    "("                         { return LPAR; }
    ")"                         { return RPAR; }

    {SPACE}+                    { return TokenType.WHITE_SPACE; }
    {LINECOMMENT}               { return LINE_COMMENT; }
    {BLOCKCOMMENTSTART}         { yybegin(BLOCKCOMMENTST); blockCommentDepth = 0; blockCommentStart = getTokenStart(); }

    [^]                         { return TokenType.BAD_CHARACTER; }
}
