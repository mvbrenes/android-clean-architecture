package com.marcobrenes.githubtrending.data.source

import com.marcobrenes.githubtrending.data.model.ProjectEntity
import com.marcobrenes.githubtrending.data.repository.ProjectsCache
import com.marcobrenes.githubtrending.data.repository.ProjectsDataSource
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import javax.inject.Inject

class ProjectsCacheDataSource @Inject constructor(
        private val projectsCache: ProjectsCache
) : ProjectsDataSource {

    override fun getProjects(): Flowable<List<ProjectEntity>> {
        return projectsCache.getProjects()
    }

    override fun saveProjects(projects: List<ProjectEntity>): Completable {
        return projectsCache.saveProjects(projects)
                .andThen(projectsCache.setLastCacheTime(System.currentTimeMillis()))
    }

    override fun clearProjects(): Completable {
        return projectsCache.clearProjects()
    }

    override fun getBookmarkedProjects(): Observable<List<ProjectEntity>> {
        return projectsCache.getBookmarkedProjects()
    }

    override fun setProjectAsBookmarked(projectId: String): Completable {
        return projectsCache.setProjectAsBookmarked(projectId)
    }

    override fun setProjectAsNotBookmarked(projectId: String): Completable {
        return projectsCache.setProjectAsNotBookmarked(projectId)
    }
}