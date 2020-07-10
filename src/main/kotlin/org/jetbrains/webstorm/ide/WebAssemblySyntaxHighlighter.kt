package org.jetbrains.webstorm.ide

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import gnu.trove.THashMap
import org.jetbrains.webstorm.ide.colors.WebAssemblyColor
import org.jetbrains.webstorm.lang.lexer.WebAssemblyLexerAdapter
import org.jetbrains.webstorm.lang.psi.WebAssemblyTypes.*

class WebAssemblySyntaxHighlighter : SyntaxHighlighterBase() {
    override fun getHighlightingLexer(): Lexer = WebAssemblyLexerAdapter()

    override fun getTokenHighlights(tokenType: IElementType): Array<out TextAttributesKey> =
        pack(tokenMap[tokenType]?.textAttributesKey)

    private val tokenMap: Map<IElementType, WebAssemblyColor> =
        hashMapOf(
            SEPARATOR to WebAssemblyColor.SEPARATOR,
            KEY to WebAssemblyColor.KEYWORD,
            VALUE to WebAssemblyColor.VALUE,
            COMMENT to WebAssemblyColor.COMMENT,
            TokenType.BAD_CHARACTER to WebAssemblyColor.BAD_CHARACTER)
}