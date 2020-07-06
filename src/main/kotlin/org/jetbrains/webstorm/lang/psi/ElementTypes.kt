package org.jetbrains.webstorm.lang.psi

import com.intellij.openapi.fileTypes.LanguageFileType
import org.jetbrains.webstorm.ide.icons.WebAssemblyIcons
import org.jetbrains.webstorm.lang.WebAssemblyLanguage
import javax.swing.Icon

object WebAssemblyFileType : LanguageFileType(WebAssemblyLanguage) {
    override fun getName(): String = "WebAssembly"
    override fun getDescription(): String = "WebAssembly file"
    override fun getDefaultExtension(): String = "wasm"
    override fun getIcon(): Icon = WebAssemblyIcons.WEB_ASSEMBLY_FILE
}