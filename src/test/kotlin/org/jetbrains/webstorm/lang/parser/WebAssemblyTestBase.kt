package org.jetbrains.webstorm.lang.parser

import com.intellij.testFramework.ParsingTestCase
import org.jetbrains.webstorm.lang.psi.WebAssemblyFileType


abstract class WebAssemblyTestBase(dataPath: String) :
    ParsingTestCase("parser/$dataPath", WebAssemblyFileType.defaultExtension, WebAssemblyParserDefinition()) {

    fun doTest() = doTest(true)

    override fun getTestDataPath(): String = "src/test/resources"

    override fun skipSpaces(): Boolean = false

    override fun includeRanges(): Boolean = true
}