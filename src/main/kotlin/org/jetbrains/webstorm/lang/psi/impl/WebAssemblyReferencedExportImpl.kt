package org.jetbrains.webstorm.lang.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.elementType
import org.jetbrains.webstorm.lang.WebAssemblyReference
import org.jetbrains.webstorm.lang.WebAssemblyUtil
import org.jetbrains.webstorm.lang.psi.WebAssemblyModulefield
import org.jetbrains.webstorm.lang.psi.WebAssemblyNamedElement
import org.jetbrains.webstorm.lang.psi.WebAssemblyReferencedElement
import org.jetbrains.webstorm.lang.psi.WebAssemblyTypes


open class WebAssemblyReferencedExportImpl(node: ASTNode) : ASTWrapperPsiElement(node), WebAssemblyReferencedElement {
    private val exportdescNode: ASTNode? = node.findChildByType(WebAssemblyTypes.EXPORTDESC)

    override fun getReferences(): Array<PsiReference> {
        val result: MutableList<PsiReference> = mutableListOf()

        exportdescNode
            ?.findChildByType(WebAssemblyTypes.IDX)
            ?.let {
                PsiTreeUtil.getParentOfType(node.psi, WebAssemblyModulefield::class.java)?.let { parent ->
                    result.add(WebAssemblyReference(node, it, when (exportdescNode.psi?.firstChild?.nextSibling?.elementType) {
                        WebAssemblyTypes.FUNCKEY   -> WebAssemblyUtil.findImportedModulefield(WebAssemblyTypes.FUNC, parent)
                        WebAssemblyTypes.TABLEKEY  -> WebAssemblyUtil.findImportedModulefield(WebAssemblyTypes.TABLE, parent)
                        WebAssemblyTypes.MEMORYKEY -> WebAssemblyUtil.findImportedModulefield(WebAssemblyTypes.MEM, parent)
                        WebAssemblyTypes.GLOBALKEY -> WebAssemblyUtil.findImportedModulefield(WebAssemblyTypes.GLOBAL, parent)
                        else                       -> null as Array<WebAssemblyNamedElement>?
                    }))
                }
            }

        return result.toTypedArray()
    }

    override fun getReference(): PsiReference? = if (references.isNotEmpty()) references[0] else null
}