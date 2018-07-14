package com.marcobrenes.githubtrending.cache.test.factory

import com.marcobrenes.githubtrending.cache.model.CachedProject
import com.marcobrenes.githubtrending.data.model.ProjectEntity

object ProjectDataFactory {

    fun makeCachedProject(): CachedProject = with(DataFactory) {
        CachedProject(randomString(), randomString(), randomString(), randomString(),
                randomString(), randomString(), randomString(), false)
    }

    fun makeCachedBookmarkedProject(): CachedProject = with(DataFactory) {
        CachedProject(randomString(), randomString(), randomString(), randomString(),
                randomString(), randomString(), randomString(), true)
    }

    fun makeProjectEntity(): ProjectEntity = with(DataFactory) {
        ProjectEntity(randomString(), randomString(), randomString(), randomString(),
                randomString(), randomString(), randomString(), false)
    }

    fun makeBookmarkedProjectEntity(): ProjectEntity = with(DataFactory) {
        ProjectEntity(randomString(), randomString(), randomString(), randomString(),
                randomString(), randomString(), randomString(), true)
    }
}
