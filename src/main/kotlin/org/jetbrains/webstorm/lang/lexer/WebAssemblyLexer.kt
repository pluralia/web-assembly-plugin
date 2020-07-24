package org.jetbrains.webstorm.lang.lexer

import com.intellij.lexer.FlexAdapter
import com.intellij.lexer.Lexer
import com.intellij.lexer.MergeFunction
import com.intellij.lexer.MergingLexerAdapterBase
import com.intellij.psi.tree.IElementType
import org.jetbrains.webstorm.lang.psi.WebAssemblyTypes.*
import java.io.Reader

class WebAssemblyLexer : MergingLexerAdapterBase(FlexAdapter(_WebAssemblyLexer(null as Reader?))) {
    override fun getMergeFunction(): MergeFunction? = MERGE_FUNCTION

    // Merges [BLOCK_COMMENT_START BLOCK_COMMENT_CHAR* BLOCK_COMMENT_FINISH] in BLOCK_COMMENT
    private val MERGE_FUNCTION = MergeFunction label@{ firstTokenType: IElementType, originalLexer: Lexer ->
        if (firstTokenType === BLOCK_COMMENT_START) {
            while (true) {
                val nextTokenType = originalLexer.tokenType ?: break   // EOF reached, block comment is not closed
                originalLexer.advance()

                if (nextTokenType === BLOCK_COMMENT_FINISH) return@label BLOCK_COMMENT
//                assert(nextTokenType === BLOCK_COMMENT_CHAR) { nextTokenType }
            }
        }
        firstTokenType
    }
}