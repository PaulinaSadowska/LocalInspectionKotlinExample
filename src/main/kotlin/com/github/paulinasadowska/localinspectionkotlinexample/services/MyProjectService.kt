package com.github.paulinasadowska.localinspectionkotlinexample.services

import com.intellij.openapi.project.Project
import com.github.paulinasadowska.localinspectionkotlinexample.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
