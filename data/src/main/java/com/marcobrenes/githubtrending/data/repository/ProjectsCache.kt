package com.marcobrenes.githubtrending.data.repository

import com.marcobrenes.githubtrending.data.model.ProjectEntity
import kotlinx.coroutines.channels.ReceiveChannel

interface ProjectsCache {
    suspend fun clearProjects()
    suspend fun saveProjects(projects: List<ProjectEntity>)
    suspend fun getProjects(): ReceiveChannel<List<ProjectEntity>>
    suspend fun getBookmarkedProjects(): ReceiveChannel<List<ProjectEntity>>
    suspend fun setProjectAsBookmarked(projectId: String)
    suspend fun setProjectAsNotBookmarked(projectId: String)
    suspend fun areProjectsCached(): Boolean
    suspend fun setLastCacheTime(lastCache: Long)
    suspend fun isProjectsCacheExpired(): Boolean
}
