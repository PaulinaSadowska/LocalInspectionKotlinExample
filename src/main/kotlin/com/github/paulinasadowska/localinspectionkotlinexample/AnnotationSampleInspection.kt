package com.github.paulinasadowska.localinspectionkotlinexample

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import org.jetbrains.annotations.NotNull
import org.jetbrains.kotlin.nj2k.postProcessing.resolve
import org.jetbrains.kotlin.psi.*

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
        expression.findCalleeNamedFunction()
            ?.findAnnotationEntry("MyAnnotation")
            ?.findStringArgumentValue()
            ?.let { message ->
                holder.registerProblem(
                    expression as PsiElement,
                    "Function call with MyAnnotation. Message: \"$message\""
                )
            }

    }

    private fun KtCallExpression.findCalleeNamedFunction(): KtNamedFunction? {
        return calleeExpression?.let {
            (it as? KtNameReferenceExpression)?.resolve() as? KtNamedFunction
        }
    }

    private fun KtNamedFunction.findAnnotationEntry(annotationToFindClassName: String): KtAnnotationEntry? {
        return annotationEntries.find {
            it.shortName?.identifier == annotationToFindClassName
        }
    }

    private fun KtAnnotationEntry.findStringArgumentValue(): String? {
        val argumentExpression = valueArguments.firstOrNull()?.getArgumentExpression()
        return (argumentExpression as? KtStringTemplateExpression)?.let { stringTemplate ->
            stringTemplate.entries.joinToString(separator = "") { (it as? KtLiteralStringTemplateEntry)?.text ?: "" }
        }
    }
}
