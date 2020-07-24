package org.jetbrains.webstorm.ide.colors

import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors as Default

enum class WebAssemblyColor(humanName: String, default: TextAttributesKey? = null) {
    KEYWORD("Keyword", Default.KEYWORD),
    RESERVED("Instruction", Default.CONSTANT),
    STRING("String", Default.STRING),
    NUMBER("Number", Default.NUMBER),
    IDENTIFIER("Identifier", Default.IDENTIFIER),
    PARENTHESES("Bracket", Default.PARENTHESES),
    COMMENT("Comment", Default.LINE_COMMENT),
    BAD_CHARACTER("Bad character", HighlighterColors.BAD_CHARACTER)
    ;

    val textAttributesKey: TextAttributesKey = TextAttributesKey.createTextAttributesKey("WebAssembly.$name", default)
    val attributesDescriptor: AttributesDescriptor = AttributesDescriptor(humanName, textAttributesKey)
    val testSeverity: HighlightSeverity = HighlightSeverity(name, HighlightSeverity.INFORMATION.myVal)
}