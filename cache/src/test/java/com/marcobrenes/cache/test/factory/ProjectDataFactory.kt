package com.marcobrenes.cache.test.factory

import com.marcobrenes.cache.model.CachedProject
import com.marcobrenes.data.model.ProjectEntity

object ProjectDataFactory {

    fun makeCachedProject(): CachedProject = with(DataFactory) {
        CachedProject(randomString(), randomString(), randomString(), randomString(),
                randomString(), randomString(), randomString(), randomBoolean())
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
