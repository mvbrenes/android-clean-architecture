package com.marcobrenes.githubtrending.data

import com.marcobrenes.githubtrending.data.mapper.ProjectMapper
import com.marcobrenes.githubtrending.data.repository.ProjectsCache
import com.marcobrenes.githubtrending.data.source.ProjectsCacheDataSource
import com.marcobrenes.githubtrending.data.source.ProjectsRemoteDataSource
import com.marcobrenes.githubtrending.domain.model.Project
import com.marcobrenes.githubtrending.domain.repository.ProjectsRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.map
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class ProjectsDataRepository @Inject constructor(
    private val mapper: ProjectMapper,
    private val cache: ProjectsCache,
    private val cacheDataSource: ProjectsCacheDataSource,
    private val remoteDataSource: ProjectsRemoteDataSource
) : ProjectsRepository {

    override suspend fun getProjects(): ReceiveChannel<List<Project>> = coroutineScope {
        val areProjectsCached = async { cache.areProjectsCached() }
        val isCacheExpired = async { cache.isProjectsCacheExpired() }
        if (!areProjectsCached.await() || isCacheExpired.await()) {
            val projects = remoteDataSource.getProjects()
            cacheDataSource.saveProjects(projects)
        }
        cacheDataSource.getProjects().map { entities ->
            entities.map { mapper.mapFromEntity(it) }
        }
    }

    override suspend fun bookmarkProject(projectId: String) {
        cacheDataSource.setProjectAsBookmarked(projectId)
    }

    override suspend fun unbookmarkProject(projectId: String) {
        cacheDataSource.setProjectAsNotBookmarked(projectId)
    }

    override suspend fun getBookmarkedProjects(): ReceiveChannel<List<Project>> {
        return cacheDataSource.getBookmarkedProjects().map { entities ->
            entities.map { mapper.mapFromEntity(it) }
        }
    }
}
