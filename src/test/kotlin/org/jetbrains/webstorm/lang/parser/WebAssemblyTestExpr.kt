package org.jetbrains.webstorm.lang.parser

class WebAssemblyTestExpr : WebAssemblyTestBase("expr") {
    fun testAtomic() = doTest()
    fun testBinary() = doTest()
    fun testBlock() = doTest()
    fun testBlockMulti() = doTest()
    fun testBlockMultiNamed() = doTest()
    fun testBlockReturn() = doTest()
    fun testBr() = doTest()
    fun testBrBlock() = doTest()
    fun testBrif() = doTest()
    fun testBrifNamed() = doTest()
    fun testBrLoop() = doTest()
    fun testBrNamed() = doTest()
    fun testBrtable() = doTest()
    fun testBrtableMulti() = doTest()
    fun testBrtableNamed() = doTest()
}