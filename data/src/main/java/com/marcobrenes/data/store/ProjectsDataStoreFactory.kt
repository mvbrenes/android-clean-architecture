package com.marcobrenes.data.store

import com.marcobrenes.data.repository.ProjectsDataStore
import javax.inject.Inject

open class ProjectsDataStoreFactory @Inject constructor(
        private val projectsCacheDataStore: ProjectsCacheDataStore,
        private val projectsRemoteDataSource: ProjectsRemoteDataSource
) {
    open fun getDataStore(projectsCached: Boolean,
                          cacheExpired: Boolean): ProjectsDataStore {
        return if (projectsCached and cacheExpired.not()) {
            projectsCacheDataStore
        } else {
            projectsRemoteDataSource
        }
    }

    open fun getCachedDataStore(): ProjectsDataStore {
        return projectsCacheDataStore
    }
}