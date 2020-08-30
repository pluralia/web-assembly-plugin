package org.jetbrains.webstorm.lang.editor

import com.intellij.testFramework.fixtures.BasePlatformTestCase

class WebAssemblyCodeFoldingTest : BasePlatformTestCase() {
    override fun getTestDataPath(): String = "src/test/resources/editor/codeFolding"

    fun testBr() = testCodeFolding(getTestName(false))
    fun testBrifNamed() = testCodeFolding(getTestName(false))
    fun testBrLoop() = testCodeFolding(getTestName(false))
    fun testBrtable() = testCodeFolding(getTestName(false))
    fun testExprBrif() = testCodeFolding(getTestName(false))
    fun testHighlighting() = testCodeFolding(getTestName(false))
    fun testIf() = testCodeFolding(getTestName(false))
    fun testIfMultiNamed() = testCodeFolding(getTestName(false))
    fun testIfReturn() = testCodeFolding(getTestName(false))
    fun testIfThenBr() = testCodeFolding(getTestName(false))
    fun testIfThenBrNamed() = testCodeFolding(getTestName(false))
    fun testIfThenElseBr() = testCodeFolding(getTestName(false))
    fun testIfThenElseBrNamed() = testCodeFolding(getTestName(false))
    fun testLoop() = testCodeFolding(getTestName(false))
    fun testLoopMultiNamed() = testCodeFolding(getTestName(false))

    private fun testCodeFolding(testName: String) {
        myFixture.testFoldingWithCollapseStatus("${testDataPath}/${testName}.wat")
    }
}