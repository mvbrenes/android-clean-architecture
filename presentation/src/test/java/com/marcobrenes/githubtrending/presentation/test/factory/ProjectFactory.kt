package com.marcobrenes.githubtrending.presentation.test.factory

import com.marcobrenes.githubtrending.domain.model.Project
import com.marcobrenes.githubtrending.presentation.model.ProjectView

object ProjectFactory {

    private fun makeProjectView(): ProjectView {
        return with(DataFactory) {
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
    }

    fun makeProject(): Project {
        return with(DataFactory) {
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
    }

    fun makeProjectViewList(count: Int): List<ProjectView> {
        val projects = mutableListOf<ProjectView>()
        repeat(count) {
            projects.add(makeProjectView())
        }
        return projects
    }

    fun makeProjectList(count: Int): List<Project> {
        val projects = mutableListOf<Project>()
        repeat(count) {
            projects.add(makeProject())
        }
        return projects
    }
}
