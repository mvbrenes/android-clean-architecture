package com.marcobrenes.data.store

import com.marcobrenes.data.model.ProjectEntity
import com.marcobrenes.data.repository.ProjectsDataStore
import com.marcobrenes.data.repository.ProjectsRemote
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class ProjectsRemoteDataSource @Inject constructor(
        private val projectsRemote: ProjectsRemote
) : ProjectsDataStore {

    override fun getProjects(): Observable<List<ProjectEntity>> {
        return projectsRemote.getProjects()
    }

    override fun saveProjects(projects: List<ProjectEntity>): Completable {
        throw UnsupportedOperationException("Saving projects isn't supported here...")
    }

    override fun clearProjects(): Completable {
        throw UnsupportedOperationException("Clearing projects isn't supported here...")
    }

    override fun getBookmarkedProjects(): Observable<List<ProjectEntity>> {
        throw UnsupportedOperationException("Getting bookmarked projects isn't supported here...")
    }

    override fun setProjectAsBookmarked(projectId: String): Completable {
        throw UnsupportedOperationException("Setting bookmarks isn't supported here...")
    }

    override fun setProjectAsNotBookmarked(projectId: String): Completable {
        throw UnsupportedOperationException("Setting bookmarks isn't supported here...")
    }
}