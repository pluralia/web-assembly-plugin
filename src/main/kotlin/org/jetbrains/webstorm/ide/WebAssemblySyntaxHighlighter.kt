package org.jetbrains.webstorm.ide

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import org.jetbrains.webstorm.ide.colors.WebAssemblyColor
import org.jetbrains.webstorm.lang.lexer.WebAssemblyLexer
import org.jetbrains.webstorm.lang.psi.WebAssemblyTypes.*

class WebAssemblySyntaxHighlighter : SyntaxHighlighterBase() {
    override fun getHighlightingLexer(): Lexer = WebAssemblyLexer()

    override fun getTokenHighlights(tokenType: IElementType): Array<out TextAttributesKey> =
        pack(tokenMap[tokenType]?.textAttributesKey)

    private val tokenMap: Map<IElementType, WebAssemblyColor> =
        hashMapOf(
            MODULEKEY to WebAssemblyColor.KEYWORD,

            // modulefield
            TYPEKEY to WebAssemblyColor.KEYWORD,
            IMPORTKEY to WebAssemblyColor.KEYWORD,
            FUNCKEY to WebAssemblyColor.KEYWORD,
            TABLEKEY to WebAssemblyColor.KEYWORD,
            MEMORYKEY to WebAssemblyColor.KEYWORD,
            GLOBALKEY to WebAssemblyColor.KEYWORD,
            EXPORTKEY to WebAssemblyColor.KEYWORD,
            STARTKEY to WebAssemblyColor.KEYWORD,
            ELEMKEY to WebAssemblyColor.KEYWORD,
            DATAKEY to WebAssemblyColor.KEYWORD,

            PARAMKEY to WebAssemblyColor.KEYWORD,
            RESULTKEY to WebAssemblyColor.KEYWORD,
            FUNCREFKEY to WebAssemblyColor.KEYWORD,
            MUTKEY to WebAssemblyColor.KEYWORD,
            LOCALKEY to WebAssemblyColor.KEYWORD,
            BLOCKKEY to WebAssemblyColor.KEYWORD,
            LOOPKEY to WebAssemblyColor.KEYWORD,
            ENDKEY to WebAssemblyColor.KEYWORD,
            IFKEY to WebAssemblyColor.KEYWORD,
            ELSEKEY to WebAssemblyColor.KEYWORD,
            OFFSETKEY to WebAssemblyColor.KEYWORD,

            OFFSETEQKEY to WebAssemblyColor.RESERVED,
            ALIGNEQKEY to WebAssemblyColor.RESERVED,

            // instructions
            CONTROLINSTR to WebAssemblyColor.KEYWORD,
            CONTROLINSTR_IDX to WebAssemblyColor.KEYWORD,
            BRTABLEINSTR to WebAssemblyColor.KEYWORD,
            CALLINDIRECTINSTR to WebAssemblyColor.KEYWORD,
            VARIABLEINSTR_IDX to WebAssemblyColor.KEYWORD,
            MEMORYINSTR to WebAssemblyColor.KEYWORD,
            PARAMETRICINSTR to WebAssemblyColor.KEYWORD,
            MEMORYINSTR_MEMARG to WebAssemblyColor.KEYWORD,

            ICONST to WebAssemblyColor.RESERVED,
            FCONST to WebAssemblyColor.RESERVED,
            NUMERICINSTR to WebAssemblyColor.RESERVED,
            VALTYPE to WebAssemblyColor.RESERVED,

            IDENTIFIER to WebAssemblyColor.IDENTIFIER,

            STRING to WebAssemblyColor.STRING,

            UNSIGNED to WebAssemblyColor.NUMBER,
            SIGNED to WebAssemblyColor.NUMBER,
            FLOAT to WebAssemblyColor.NUMBER,

            LPAR to WebAssemblyColor.PARENTHESES,
            RPAR to WebAssemblyColor.PARENTHESES,

            LINE_COMMENT to WebAssemblyColor.COMMENT,
            BLOCK_COMMENT to WebAssemblyColor.COMMENT,

            TokenType.BAD_CHARACTER to WebAssemblyColor.BAD_CHARACTER)
}