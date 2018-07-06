package com.marcobrenes.domain.test

import com.marcobrenes.domain.model.Project
import java.util.*

internal object ProjectDataFactory {

    fun randomUuid() = UUID.randomUUID().toString()
    fun randomBoolean() = Math.random() < 0.5

    fun makeProject() = Project(randomUuid(), randomUuid(), randomUuid(),
                randomUuid(), randomUuid(), randomUuid(),
                randomUuid(), randomBoolean())

    fun makeProjectList(count: Int): List<Project> {
        val projects = mutableListOf<Project>()
        repeat(count) { projects.add(makeProject()) }
        return projects
    }
}