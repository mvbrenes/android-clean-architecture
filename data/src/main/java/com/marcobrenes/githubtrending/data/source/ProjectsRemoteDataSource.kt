package com.marcobrenes.githubtrending.data.source

import com.marcobrenes.githubtrending.data.model.ProjectEntity
import com.marcobrenes.githubtrending.data.repository.ProjectsDataSource
import com.marcobrenes.githubtrending.data.repository.ProjectsRemote
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import javax.inject.Inject

class ProjectsRemoteDataSource @Inject constructor(
        private val projectsRemote: ProjectsRemote
) : ProjectsDataSource {

    override fun getProjects(): Flowable<List<ProjectEntity>> {
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