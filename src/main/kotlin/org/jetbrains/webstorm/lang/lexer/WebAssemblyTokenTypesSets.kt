package org.jetbrains.webstorm.lang.lexer

import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import org.jetbrains.webstorm.lang.WebAssemblyLanguage
import org.jetbrains.webstorm.lang.psi.WebAssemblyElementType

import org.jetbrains.webstorm.lang.psi.WebAssemblyTypes.*

class WebAssemblyTokenTypesSets {
    var WEB_ASSEMBLY_FILE = IFileElementType("WebAssembly file", WebAssemblyLanguage)

    var WHITE_SPACE: IElementType = TokenType.WHITE_SPACE
    var BAD_CHARACTER: IElementType = TokenType.BAD_CHARACTER

    // WebAssemblyLexer returns block comments as a single BLOCK_COMMENT
    // Can't appear in PSI because merged into BLOCK_COMMENT
    var BLOCK_COMMENT_START: IElementType = WebAssemblyElementType("BLOCK_COMMENT_START")
    var BLOCK_COMMENT_FINISH: IElementType = WebAssemblyElementType("BLOCK_COMMENT_FINISH")
    var BLOCK_COMMENT_CHAR: IElementType = WebAssemblyElementType("BLOCK_COMMENT_CHAR")

    var LINE_COMMENT: IElementType = WebAssemblyElementType("LINE_COMMENT")
    var BLOCK_COMMENT: IElementType = WebAssemblyElementType("BLOCK_COMMENT")

    var STRINGS = TokenSet.create(STRING)
    var WHITE_SPACES = TokenSet.create(WHITE_SPACE)

    var RESERVED_WORDS = TokenSet.create(
            OFFSETEQKEY,
            ALIGNEQKEY,
            ICONST,
            FCONST,
            NUMERICINSTR,
            VALTYPE,
            // numbers
            UNSIGNED,
            SIGNED,
            FLOAT
    )

    var BUILT_IN_IDENTIFIERS = TokenSet.create(
            MODULEKEY,
            TYPEKEY,
            IMPORTKEY,
            FUNCKEY,
            TABLEKEY,
            MEMORYKEY,
            GLOBALKEY,
            EXPORTKEY,
            STARTKEY,
            ELEMKEY,
            DATAKEY,
            PARAMKEY,
            RESULTKEY,
            FUNCREFKEY,
            MUTKEY,
            LOCALKEY,
            BLOCKKEY,
            LOOPKEY,
            ENDKEY,
            IFKEY,
            ELSEKEY,
            OFFSETKEY,
            CONTROLINSTR,
            CONTROLINSTR_IDX,
            BRTABLEINSTR,
            CALLINDIRECTINSTR,
            VARIABLEINSTR_IDX,
            MEMORYINSTR,
            PARAMETRICINSTR,
            MEMORYINSTR_MEMARG
    )

    var OPERATORS = TokenSet.create(
            LPAR,
            RPAR
    )

    var COMMENTS = TokenSet.create(
            LINE_COMMENT,
            BLOCK_COMMENT
    )
}