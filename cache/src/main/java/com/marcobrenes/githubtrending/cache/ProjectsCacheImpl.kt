package com.marcobrenes.githubtrending.cache

import com.marcobrenes.githubtrending.cache.db.ProjectsDatabase
import com.marcobrenes.githubtrending.cache.mapper.CachedProjectMapper
import com.marcobrenes.githubtrending.cache.model.Config
import com.marcobrenes.githubtrending.data.model.ProjectEntity
import com.marcobrenes.githubtrending.data.repository.ProjectsCache
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.map
import kotlinx.coroutines.reactive.openSubscription
import javax.inject.Inject


class ProjectsCacheImpl @Inject constructor(
    private val projectsDatabase: ProjectsDatabase,
    private val mapper: CachedProjectMapper
) : ProjectsCache {

    override suspend fun clearProjects() {
        projectsDatabase.cachedProjectsDao().deleteProjects()
    }

    override suspend fun saveProjects(projects: List<ProjectEntity>) {
        projectsDatabase.cachedProjectsDao().insertProjects(
            projects.map { mapper.mapToCached(it) }
        )
    }

    override suspend fun getProjects(): ReceiveChannel<List<ProjectEntity>> {
        return projectsDatabase.cachedProjectsDao()
            .getProjects()
            .openSubscription()
            .map { list ->
                list.map { mapper.mapFromCached(it) }
            }
    }

    override suspend fun getBookmarkedProjects(): ReceiveChannel<List<ProjectEntity>> {
        return projectsDatabase.cachedProjectsDao()
            .getBookmarkedProjects()
            .openSubscription()
            .map { list ->
                list.map { mapper.mapFromCached(it) }
            }
    }

    override suspend fun setProjectAsBookmarked(projectId: String) {
        projectsDatabase.cachedProjectsDao().setBookmarkStatus(true, projectId)
    }

    override suspend fun setProjectAsNotBookmarked(projectId: String) {
        projectsDatabase.cachedProjectsDao().setBookmarkStatus(false, projectId)
    }

    override suspend fun areProjectsCached(): Boolean {
        return projectsDatabase.cachedProjectsDao().cachedProjectsExist() > 0
    }

    override suspend fun setLastCacheTime(lastCache: Long) {
        projectsDatabase.configDao().insertConfig(Config(lastCacheTime = lastCache))
    }

    override suspend fun isProjectsCacheExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val expirationTime = (60 * 10 * 1_000).toLong()
        val config = projectsDatabase.configDao().getConfig() ?: Config(lastCacheTime = 0)
        return currentTime - config.lastCacheTime > expirationTime
    }
}
