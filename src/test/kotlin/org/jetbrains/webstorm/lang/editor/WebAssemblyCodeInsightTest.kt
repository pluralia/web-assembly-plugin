package org.jetbrains.webstorm.lang.editor

import com.intellij.testFramework.fixtures.BasePlatformTestCase


class WebAssemblyCodeInsightTest : BasePlatformTestCase() {
    override fun getTestDataPath(): String = "src/test/resources/editor"
    private val testFoldDataPath: String = "${testDataPath}/fold/"

    fun testHighlighting() {
        myFixture.testFoldingWithCollapseStatus(testFoldDataPath + getTestName(false) + ".wat")
    }
}