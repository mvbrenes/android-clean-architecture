package com.marcobrenes.mobileui.test.factory

import com.marcobrenes.githubtrending.domain.model.Project
import com.marcobrenes.githubtrending.presentation.model.ProjectView

object TestProjectFactory {

    fun makeProjectView(): ProjectView = with(TestDataFactory) {
        ProjectView(
                randomString(),
                randomString(),
                randomString(),
                randomString(),
                randomString(),
                randomString(),
                randomString(),
                randomBoolean()
        )
    }

    fun makeProject() = with(TestDataFactory) {
        Project(
                randomString(),
                randomString(),
                randomString(),
                randomString(),
                randomString(),
                randomString(),
                randomString(),
                randomBoolean()
        )
    }

    fun makeProjectList(count: Int): List<Project> {
        val projects = mutableListOf<Project>()
        repeat(count) { projects.add(makeProject()) }
        return projects
    }
}
