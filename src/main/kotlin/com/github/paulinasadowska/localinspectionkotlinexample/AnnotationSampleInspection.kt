package com.github.paulinasadowska.localinspectionkotlinexample

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import org.jetbrains.annotations.NotNull
import org.jetbrains.kotlin.nj2k.postProcessing.resolve
import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.psi.KtNameReferenceExpression
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.KtVisitorVoid

class AnnotationSampleInspection : LocalInspectionTool() {

    @NotNull
    override fun buildVisitor(@NotNull holder: ProblemsHolder, isOnTheFly: Boolean): AnnotationSampleInspectionVisitor {
        return AnnotationSampleInspectionVisitor(holder)
    }
}

class AnnotationSampleInspectionVisitor(
    private val holder: ProblemsHolder,
) : KtVisitorVoid() {

    override fun visitCallExpression(expression: KtCallExpression) {
        super.visitCallExpression(expression)
        expression.findCalleeNamedFunction()?.let {
            holder.registerProblem(
                expression as PsiElement,
                "function call: ${it.name}"
            )
        }

    }

    private fun KtCallExpression.findCalleeNamedFunction(): KtNamedFunction? {
        return calleeExpression?.let {
            (it as? KtNameReferenceExpression)?.resolve() as? KtNamedFunction
        }
    }
}
