package org.jetbrains.webstorm.lang.parser

class WebAssemblyTestFile : WebAssemblyTestBase("file") {
    fun testAdd() = doTest()
    fun testCall() = doTest()
    fun testFail() = doTest()
    fun testGlobal() = doTest()
    fun testHighlighting() = doTest()
    fun testLogger() = doTest()
    fun testLogger2() = doTest()
    fun testMemory() = doTest()
    fun testShared0() = doTest()
    fun testShared1() = doTest()
    fun testSimple() = doTest()
    fun testTable() = doTest()
    fun testTable2() = doTest()
    fun testWasmTable() = doTest()
}