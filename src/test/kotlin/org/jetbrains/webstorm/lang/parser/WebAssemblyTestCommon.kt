package org.jetbrains.webstorm.lang.parser

class WebAssemblyTestCommon : WebAssemblyTestBase("common") {
    fun testBasic() = doTest()
    fun testEmptyFile() = doTest()
    fun testExportMutableGlobal() = doTest()
    fun testForceColor() = doTest()
    fun testLineComment() = doTest()
    fun testNestedComments() = doTest()
    fun testStdin() = doTest()
    fun testStringEscape() = doTest()
    fun testStringHex() = doTest()

    fun testBadCrlf() = doTest()
    fun testBadErrorLongLine() = doTest()
    fun testBadErrorLongToken() = doTest()
    fun testBadInputCommand() = doTest()
    fun testBadOutputCommand() = doTest()
    fun testBadSingleSemicolon() = doTest()
    fun testBadStringEof() = doTest()
    fun testBadStringEscape() = doTest()
    fun testBadStringHexEscape() = doTest()
    fun testBadToplevel() = doTest()
}