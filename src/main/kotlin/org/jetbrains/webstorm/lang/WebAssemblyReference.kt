package org.jetbrains.webstorm.lang

import com.intellij.lang.ASTNode
import com.intellij.openapi.util.TextRange
import com.intellij.psi.*
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import org.jetbrains.webstorm.lang.psi.*

class WebAssemblyReference(node: ASTNode,
                           idxNode: ASTNode,
                           private val namedElements: Array<WebAssemblyNamedElement>?)
    : PsiPolyVariantReference, PsiReferenceBase<PsiElement>(node.psi,
            TextRange(idxNode.textRange.startOffset - node.startOffset,
                      idxNode.textRange.endOffset - node.startOffset)) {

    private val ident: String = element.text.substring(super.getRangeInElement().startOffset,
                                                       super.getRangeInElement().endOffset)

    override fun resolve(): PsiElement? {
        val resolveResults = multiResolve(false)
        return if (resolveResults.isNotEmpty()) resolveResults[0].element else null
    }

    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        val results: MutableList<ResolveResult> = ArrayList()

        ident.toIntOrNull()?.let { id ->
            namedElements?.let {
                if (namedElements.size > id) {
                    results.add(PsiElementResolveResult(namedElements[id]))
                }
            }
            return results.toTypedArray()
        }

        namedElements?.map {
            if (it.nameIdentifier?.text == ident) {
                results.add(PsiElementResolveResult(it))
            }
        }
        return results.toTypedArray()
    }
}