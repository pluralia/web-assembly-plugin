package org.jetbrains.webstorm.ide

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import org.jetbrains.webstorm.ide.colors.WebAssemblyColor
import org.jetbrains.webstorm.lang.lexer.WebAssemblyLexerAdapter
import org.jetbrains.webstorm.lang.psi.WebAssemblyTypes.*

class WebAssemblySyntaxHighlighter : SyntaxHighlighterBase() {
    override fun getHighlightingLexer(): Lexer = WebAssemblyLexerAdapter()

    override fun getTokenHighlights(tokenType: IElementType): Array<out TextAttributesKey> =
        pack(tokenMap[tokenType]?.textAttributesKey)

    private val tokenMap: Map<IElementType, WebAssemblyColor> =
        hashMapOf(
            FUNCKEY to WebAssemblyColor.KEYWORD,
            PARAMKEY to WebAssemblyColor.KEYWORD,
            RESULTKEY to WebAssemblyColor.KEYWORD,
            FUNCREFKEY to WebAssemblyColor.KEYWORD,
            MUTKEY to WebAssemblyColor.KEYWORD,

            BLOCKKEY to WebAssemblyColor.KEYWORD,
            LOOPKEY to WebAssemblyColor.KEYWORD,
            ENDKEY to WebAssemblyColor.KEYWORD,
            IFKEY to WebAssemblyColor.KEYWORD,
            ELSEKEY to WebAssemblyColor.KEYWORD,
            BRKEY to WebAssemblyColor.KEYWORD,
            BRIFKEY to WebAssemblyColor.KEYWORD,
            CALLKEY to WebAssemblyColor.KEYWORD,
            BRTABLEKEY to WebAssemblyColor.KEYWORD,
            CALLINDIRECTKEY to WebAssemblyColor.KEYWORD,
            OFFSETEQKEY to WebAssemblyColor.KEYWORD,
            ALIGNEQKEY to WebAssemblyColor.KEYWORD,
            ICONST to WebAssemblyColor.KEYWORD,
            FCONST to WebAssemblyColor.KEYWORD,

            TYPEKEY to WebAssemblyColor.KEYWORD,
            IMPORTKEY to WebAssemblyColor.KEYWORD,
            TABLEKEY to WebAssemblyColor.KEYWORD,
            MEMORYKEY to WebAssemblyColor.KEYWORD,
            GLOBALKEY to WebAssemblyColor.KEYWORD,
            LOCALKEY to WebAssemblyColor.KEYWORD,
            EXPORTKEY to WebAssemblyColor.KEYWORD,
            ELEMKEY to WebAssemblyColor.KEYWORD,
            DATAKEY to WebAssemblyColor.KEYWORD,
            OFFSETKEY to WebAssemblyColor.KEYWORD,
            STARTKEY to WebAssemblyColor.KEYWORD,
            MODULEKEY to WebAssemblyColor.KEYWORD,
            GLOBALKEY to WebAssemblyColor.KEYWORD,

            VALTYPE to WebAssemblyColor.KEYWORD,
            ONEWORDINSTR to WebAssemblyColor.KEYWORD,
            VARIABLEINSTR to WebAssemblyColor.KEYWORD,
            MEMORYINSTR to WebAssemblyColor.KEYWORD,

            UNSIGNED to WebAssemblyColor.CONSTANT,
            SIGNED to WebAssemblyColor.CONSTANT,
            FLOAT to WebAssemblyColor.CONSTANT,
            STRING to WebAssemblyColor.CONSTANT,
            IDENTIFIER to WebAssemblyColor.IDENTIFIER,
            LEFT_BRACKET to WebAssemblyColor.BRACKET,
            RIGHT_BRACKET to WebAssemblyColor.BRACKET,

            LINE_COMMENT to WebAssemblyColor.COMMENT,
            COMMENT_RIGHT_BR to WebAssemblyColor.COMMENT,
            COMMENT_LEFT_BR to WebAssemblyColor.COMMENT,
            COMMENT_CHAR to WebAssemblyColor.COMMENT,
            TokenType.BAD_CHARACTER to WebAssemblyColor.BAD_CHARACTER)
}