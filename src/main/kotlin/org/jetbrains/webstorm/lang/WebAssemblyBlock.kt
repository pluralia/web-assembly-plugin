package org.jetbrains.webstorm.lang

import com.intellij.formatting.*
import com.intellij.lang.ASTNode
import com.intellij.psi.TokenType
import com.intellij.psi.formatter.common.AbstractBlock
import org.jetbrains.webstorm.lang.psi.WebAssemblyComment
import org.jetbrains.webstorm.lang.psi.WebAssemblyModule
import org.jetbrains.webstorm.lang.psi.WebAssemblyTypes
import java.util.*

class WebAssemblyBlock(node: ASTNode, wrap: Wrap?, alignment: Alignment?,
                       private val spacingBuilder: SpacingBuilder) : AbstractBlock(node, wrap, alignment) {

    override fun buildChildren(): List<Block> {
        val blocks: MutableList<Block> = ArrayList()

        var child: ASTNode? = myNode.firstChildNode
        while (child != null) {
            if (child.elementType != TokenType.WHITE_SPACE) {
                blocks.add(WebAssemblyBlock(child, wrap, Alignment.createAlignment(), spacingBuilder))
            }
            child = child.treeNext
        }

        return blocks
    }

    override fun getIndent(): Indent? {
        return if (node is WebAssemblyComment) {
            Indent.getNoneIndent()
        } else {
            Indent.getNormalIndent()
        }
    }

    override fun getSpacing(child1: Block?, child2: Block): Spacing? =
            spacingBuilder.getSpacing(this, child1, child2)

    override fun isLeaf(): Boolean = myNode.firstChildNode == null
}