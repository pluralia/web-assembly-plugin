package org.jetbrains.webstorm.lang

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.psi.PsiElement
import com.intellij.psi.util.elementType
import org.jetbrains.webstorm.lang.psi.WebAssemblyTypes

import com.intellij.lang.annotation.HighlightSeverity.INFORMATION
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors


class WebAssemblyAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        if (element.elementType == WebAssemblyTypes.UNSIGNED) {
            holder
                    .newAnnotation(INFORMATION, "UNSIGNED")
                    .range(element.textRange)
                    .textAttributes(DefaultLanguageHighlighterColors.KEYWORD)
                    .create()
        }
    }
}