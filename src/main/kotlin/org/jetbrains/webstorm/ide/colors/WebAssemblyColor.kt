package org.jetbrains.webstorm.ide.colors

import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors as Default

enum class WebAssemblyColor(humanName: String, default: TextAttributesKey? = null) {
    COMMENT("Comment", Default.LINE_COMMENT),

    CONSTANT("Constant", Default.CONSTANT),
    IDENTIFIER("Identifier", Default.IDENTIFIER),
    KEYWORD("Keyword", Default.KEYWORD),
    BRACKET("Bracket", Default.BRACKETS),

    BAD_CHARACTER("Bad character", HighlighterColors.BAD_CHARACTER)
    ;

    val textAttributesKey: TextAttributesKey = TextAttributesKey.createTextAttributesKey("WebAssembly.$name", default)
    val attributesDescriptor: AttributesDescriptor = AttributesDescriptor(humanName, textAttributesKey)
    val testSeverity: HighlightSeverity = HighlightSeverity(name, HighlightSeverity.INFORMATION.myVal)
}