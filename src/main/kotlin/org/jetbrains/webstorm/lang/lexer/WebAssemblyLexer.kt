package org.jetbrains.webstorm.lang.lexer

import com.intellij.lexer.FlexAdapter
import com.intellij.lexer.Lexer
import com.intellij.lexer.MergeFunction
import com.intellij.lexer.MergingLexerAdapterBase
import com.intellij.psi.tree.IElementType
import java.io.Reader

class WebAssemblyLexer : MergingLexerAdapterBase(FlexAdapter(_WebAssemblyLexer(null as Reader?))) {
    override fun getMergeFunction(): MergeFunction? = MERGE_FUNCTION

    // Merges BLOCK_COMMENT_(START CHAR* FINISH) in BLOCK_COMMENT
    private val MERGE_FUNCTION = label@ MergeFunction { firstTokenType: IElementType, originalLexer: Lexer ->
        if (firstTokenType === BLOCK_COMMENT_START) {
            // merge multiline comments that are parsed in parts into single element
            while (true) {
                val nextTokenType = originalLexer.tokenType ?: break
                // EOF reached, multi-line comment is not closed
                originalLexer.advance()
                if (nextTokenType === BLOCK_COMMENT_FINISH) break
                assert(nextTokenType === BLOCK_COMMENT_CHAR) { nextTokenType }
            }
            return@label BLOCK_COMMENT
        }
        firstTokenType
    }
}