package org.jetbrains.webstorm.lang.parser

class WebAssemblyTestFilePrevVersion : WebAssemblyTestBase("file_v1.0") {
    fun testAdd() = doTest()
    fun testShared0() = doTest()
    fun testShared1() = doTest()
    fun testTable() = doTest()
    fun testTable2() = doTest()
    fun testWasmTable() = doTest()
}