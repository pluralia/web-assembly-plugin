package org.jetbrains.webstorm.lang.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import com.intellij.psi.tree.TokenSet
import com.intellij.psi.util.PsiTreeUtil
import org.jetbrains.webstorm.lang.WebAssemblyReference
import org.jetbrains.webstorm.lang.WebAssemblyUtil
import org.jetbrains.webstorm.lang.psi.*

open class WebAssemblyNamedReferencedElementImpl(node: ASTNode)
    : ASTWrapperPsiElement(node), WebAssemblyNamedReferencedElement {

    override fun getReferences(): Array<PsiReference> {
        val result: MutableList<PsiReference> = ArrayList()

        node
            .getChildren(TokenSet.create(WebAssemblyTypes.IDX))
            .forEach {
                PsiTreeUtil.getParentOfType(node.psi, WebAssemblyModulefield::class.java)?.let { parent ->
                    result.add(WebAssemblyReference(node, it, when (node.elementType) {
                        WebAssemblyTypes.TYPEUSE_TYPEREF     -> WebAssemblyUtil.findModulefield(WebAssemblyTypes.TYPE, parent)
                        WebAssemblyTypes.START               -> WebAssemblyUtil.findImportedModulefield(WebAssemblyTypes.FUNC, parent)
                        WebAssemblyTypes.ELEM                -> WebAssemblyUtil.findImportedModulefield(WebAssemblyTypes.TABLE, parent)
                        WebAssemblyTypes.ELEMLIST            -> WebAssemblyUtil.findImportedModulefield(WebAssemblyTypes.FUNC, parent)
                        WebAssemblyTypes.DATA                -> WebAssemblyUtil.findImportedModulefield(WebAssemblyTypes.MEM, parent)
                        WebAssemblyTypes.CALL_INSTR          -> WebAssemblyUtil.findImportedModulefield(WebAssemblyTypes.FUNC, parent)
                        WebAssemblyTypes.CALL_INDIRECT_INSTR -> WebAssemblyUtil.findImportedModulefield(WebAssemblyTypes.TABLE, parent)
                        WebAssemblyTypes.REF_FUNC_INSTR      -> WebAssemblyUtil.findImportedModulefield(WebAssemblyTypes.FUNC, parent)
                        WebAssemblyTypes.LOCAL_INSTR         -> WebAssemblyUtil.findParamsLocals(parent)
                        WebAssemblyTypes.GLOBAL_INSTR        -> WebAssemblyUtil.findImportedModulefield(WebAssemblyTypes.GLOBAL, parent)
                        WebAssemblyTypes.TABLE_IDX_INSTR     -> WebAssemblyUtil.findImportedModulefield(WebAssemblyTypes.TABLE, parent)
                        WebAssemblyTypes.TABLE_COPY_INSTR    -> WebAssemblyUtil.findImportedModulefield(WebAssemblyTypes.TABLE, parent)
                        WebAssemblyTypes.ELEM_DROP_INSTR     -> WebAssemblyUtil.findModulefield(WebAssemblyTypes.ELEM, parent)
                        WebAssemblyTypes.MEMORY_IDX_INSTR    -> WebAssemblyUtil.findModulefield(WebAssemblyTypes.DATA, parent)
                        else                                 -> null as Array<WebAssemblyNamedElement>?
                    }))
                }
            }

        return result.toTypedArray()
    }

    override fun getReference(): PsiReference? = if (references.isNotEmpty()) references[0] else null

    override fun getNameIdentifier(): PsiElement? {
        return node.findChildByType(WebAssemblyTypes.IDENTIFIER)?.psi
    }

    override fun setName(name: String): PsiElement? {
        val newIdent: ASTNode = WebAssemblyElementFactory
                .createElement(node.psi.project, name)
                .findChildByType(WebAssemblyTypes.IDENTIFIER) as ASTNode

        node
                .findChildByType(WebAssemblyTypes.IDENTIFIER)
                ?.let {
                    node.replaceChild(it, newIdent)
                    return node.psi
                }

        node.addChild(newIdent, node.firstChildNode.treeNext.treeNext)
        return node.psi
    }
}