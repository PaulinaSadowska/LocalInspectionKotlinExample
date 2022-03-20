package com.github.paulinasadowska.localinspectionkotlinexample

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import org.jetbrains.annotations.NotNull
import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.psi.KtVisitorVoid

class AnnotationInspection : LocalInspectionTool() {

    @NotNull
    override fun buildVisitor(@NotNull holder: ProblemsHolder, isOnTheFly: Boolean): AnnotationInspectionVisitor {
        return AnnotationInspectionVisitor(holder)
    }
}

class AnnotationInspectionVisitor(
    private val holder: ProblemsHolder,
) : KtVisitorVoid() {

    override fun visitCallExpression(expression: KtCallExpression) {
        super.visitCallExpression(expression)
        holder.registerProblem(
            expression as PsiElement,
            "some expression"
        )
    }
}
