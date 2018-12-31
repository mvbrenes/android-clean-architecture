package com.marcobrenes.githubtrending.data.source

import com.marcobrenes.githubtrending.data.model.ProjectEntity
import com.marcobrenes.githubtrending.data.repository.ProjectsCache
import kotlinx.coroutines.channels.ReceiveChannel
import javax.inject.Inject

class ProjectsCacheDataSource @Inject constructor(
        private val projectsCache: ProjectsCache
) {

    suspend fun getProjects(): ReceiveChannel<List<ProjectEntity>> {
        return projectsCache.getProjects()
    }

    suspend fun saveProjects(projects: List<ProjectEntity>) {
        projectsCache.saveProjects(projects)
        projectsCache.setLastCacheTime(System.currentTimeMillis())
    }

    suspend fun clearProjects() {
        projectsCache.clearProjects()
    }

    suspend fun getBookmarkedProjects(): ReceiveChannel<List<ProjectEntity>> {
        return projectsCache.getBookmarkedProjects()
    }

    suspend fun setProjectAsBookmarked(projectId: String) {
        projectsCache.setProjectAsBookmarked(projectId)
    }

    suspend fun setProjectAsNotBookmarked(projectId: String) {
        projectsCache.setProjectAsNotBookmarked(projectId)
    }
}