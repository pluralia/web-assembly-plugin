package org.jetbrains.webstorm.ide

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import gnu.trove.THashMap
import org.jetbrains.webstorm.ide.colors.WebAssemblyColor
import org.jetbrains.webstorm.lang.lexer.WebAssemblyLexerAdapter
import org.jetbrains.webstorm.lang.psi.WebAssemblyElementTypes.*

class WebAssemblyHighlighter : SyntaxHighlighterBase() {
    override fun getHighlightingLexer(): Lexer = WebAssemblyLexerAdapter()

    override fun getTokenHighlights(tokenType: IElementType): Array<out TextAttributesKey> =
        pack(tokenMap[tokenType]?.textAttributesKey)

    private val tokenMap: Map<IElementType, WebAssemblyColor> =
        THashMap <IElementType, WebAssemblyColor>().apply {
            put(SEPARATOR, WebAssemblyColor.SEPARATOR)
            put(KEY, WebAssemblyColor.KEYWORD)
            put(VALUE, WebAssemblyColor.VALUE)
            put(COMMENT, WebAssemblyColor.COMMENT)
            put(TokenType.BAD_CHARACTER, WebAssemblyColor.BAD_CHARACTER)
        }
}