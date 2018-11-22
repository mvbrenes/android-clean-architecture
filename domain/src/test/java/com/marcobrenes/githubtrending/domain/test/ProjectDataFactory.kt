@file:Suppress("MemberVisibilityCanBePrivate")

package com.marcobrenes.githubtrending.domain.test

import com.marcobrenes.githubtrending.domain.model.Project
import java.util.*

internal object ProjectDataFactory {

    fun randomString() = UUID.randomUUID().toString()
    fun randomBoolean() = Math.random() < 0.5

    fun makeProject() = Project(
            randomString(),
            randomString(),
            randomString(),
            randomString(),
            randomString(),
            randomString(),
            randomString(),
            randomBoolean()
    )

    fun makeProjectList(count: Int): List<Project> {
        val projects = mutableListOf<Project>()
        repeat(count) { projects.add(makeProject()) }
        return projects
    }
}