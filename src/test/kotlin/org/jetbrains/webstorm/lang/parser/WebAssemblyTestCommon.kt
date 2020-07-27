package org.jetbrains.webstorm.lang.parser

class WebAssemblyTestCommon : WebAssemblyTestBase("common") {
    fun testAllFeatures() = doTest()
    fun testBasic() = doTest()
    fun testEmptyFile() = doTest()
    fun testExportMutableGlobal() = doTest()
    fun testLineComment() = doTest()
    fun testNestedComments() = doTest()
    fun testStringEscape() = doTest()
    fun testStringHex() = doTest()
    fun testStringHexEscape() = doTest()

    fun testBadCrlf() = doTest()
    fun testBadErrorLongToken() = doTest()
    fun testBadForceColor() = doTest()
    fun testBadSingleSemicolon() = doTest()
    fun testBadStringEof() = doTest()
    fun testBadStringEscape() = doTest()
    fun testBadToplevel() = doTest()
}