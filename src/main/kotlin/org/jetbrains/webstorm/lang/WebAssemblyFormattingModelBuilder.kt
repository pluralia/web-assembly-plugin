package org.jetbrains.webstorm.lang

import com.intellij.formatting.*
import com.intellij.lang.ASTNode
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.codeStyle.CodeStyleSettings
import org.jetbrains.webstorm.lang.psi.WebAssemblyTypes


class WebAssemblyFormattingModelBuilder : FormattingModelBuilder {
    override fun createModel(element: PsiElement, settings: CodeStyleSettings): FormattingModel {
        return FormattingModelProvider
                .createFormattingModelForPsiFile(element.containingFile,
                        WebAssemblyBlock(element.node,
                                Wrap.createWrap(WrapType.NONE, false),
                                null,
                                createSpaceBuilder(settings)),
                        settings)
    }

    override fun getRangeAffectingIndent(file: PsiFile?, offset: Int, elementAtOffset: ASTNode?): TextRange? = null

    companion object {
        private fun createSpaceBuilder(settings: CodeStyleSettings): SpacingBuilder {
            return SpacingBuilder(settings, WebAssemblyLanguage)
                    .around(WebAssemblyTypes.IDENTIFIER)
                    .spaceIf(settings.getCommonSettings(WebAssemblyLanguage.id).SPACE_AROUND_ASSIGNMENT_OPERATORS)
        }
    }
}