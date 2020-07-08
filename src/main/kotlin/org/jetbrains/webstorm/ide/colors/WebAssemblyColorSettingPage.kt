package org.jetbrains.webstorm.ide.colors

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import org.jetbrains.webstorm.ide.WebAssemblyHighlighter
import org.jetbrains.webstorm.ide.icons.WebAssemblyIcons
import javax.swing.Icon

class WebAssemblyColorSettingPage : ColorSettingsPage {
    private val attributesDescriptors = WebAssemblyColor.values().map { it.attributesDescriptor }.toTypedArray()
    private val tagToDescriptorMap = WebAssemblyColor.values().associateBy({ it.name }, { it.textAttributesKey })

    override fun getDisplayName(): String = "WebAssembly"
    override fun getHighlighter(): SyntaxHighlighter = WebAssemblyHighlighter()
    override fun getIcon(): Icon? = WebAssemblyIcons.WEB_ASSEMBLY_FILE
    override fun getAdditionalHighlightingTagToDescriptorMap(): Map<String, TextAttributesKey> = tagToDescriptorMap
    override fun getAttributeDescriptors(): Array<AttributesDescriptor> = attributesDescriptors
    override fun getColorDescriptors(): Array<ColorDescriptor> = ColorDescriptor.EMPTY_ARRAY
    override fun getDemoText(): String =
        """# You are reading the ".properties" entry.
        ! The exclamation mark can also mark text as comments.
        website = https://en.wikipedia.org/
        language = English
        # The backslash below tells the application to continue reading
        # the value onto the next line.
        message = Welcome to \
                  Wikipedia!
        # Add spaces to the key
        key\ with\ spaces = This is the value that could be looked up with the key "key with spaces".
        # Unicode
        tab : \u0009"""
}