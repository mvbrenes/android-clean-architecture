package com.marcobrenes.githubtrending.data

import com.marcobrenes.githubtrending.data.mapper.ProjectMapper
import com.marcobrenes.githubtrending.data.repository.ProjectsCache
import com.marcobrenes.githubtrending.data.source.ProjectsDataSourceFactory
import com.marcobrenes.githubtrending.domain.model.Project
import com.marcobrenes.githubtrending.domain.repository.ProjectsRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class ProjectsDataRepository @Inject constructor(
    private val mapper: ProjectMapper,
    private val cache: ProjectsCache,
    private val factory: ProjectsDataSourceFactory
) : ProjectsRepository {

    override fun getProjects(): Observable<List<Project>> {
        return Observable.zip(
            cache.areProjectsCached().toObservable(),
            cache.isProjectsCacheExpired().toObservable(),
            BiFunction<Boolean, Boolean, Pair<Boolean, Boolean>> { cached, expired ->
                Pair(cached, expired)
            }).flatMap {
            factory.getDataSource(it.first, it.second)
                .getProjects()
                .toObservable()
                .distinctUntilChanged()
        }.flatMap { projects ->
            factory.getCacheDataSource()
                .saveProjects(projects)
                .andThen(
                    factory.getCacheDataSource()
                        .getProjects()
                        .toObservable()
                )
        }.map { list -> list.map { mapper.mapFromEntity(it) } }
    }

    override fun bookmarkProject(projectId: String): Completable {
        return factory.getCacheDataSource().setProjectAsBookmarked(projectId)
    }

    override fun unbookmarkProject(projectId: String): Completable {
        return factory.getCacheDataSource().setProjectAsNotBookmarked(projectId)
    }

    override fun getBookmarkedProjects(): Observable<List<Project>> {
        return factory.getCacheDataSource().getBookmarkedProjects()
            .map { list -> list.map { mapper.mapFromEntity(it) } }
    }
}
