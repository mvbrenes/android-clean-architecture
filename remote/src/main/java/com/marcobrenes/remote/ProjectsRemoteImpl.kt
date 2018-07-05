package com.marcobrenes.remote

import com.marcobrenes.data.model.ProjectEntity
import com.marcobrenes.data.repository.ProjectsRemote
import com.marcobrenes.remote.mapper.ProjectsResponseModelMapper
import com.marcobrenes.remote.service.GithubTrendingService
import io.reactivex.Observable
import javax.inject.Inject

private const val QUERY = "language:kotlin"
private const val SORT_BY = "stars"
private const val ORDER = "desc"

class ProjectsRemoteImpl @Inject constructor(
        private val service: GithubTrendingService,
        private val mapper: ProjectsResponseModelMapper
) : ProjectsRemote {

    override fun getProjects(): Observable<List<ProjectEntity>> {
        return service.searchRepositories(QUERY, SORT_BY, ORDER)
                .map {
                    it.items.map { mapper.mapFromModel(it) }
                }
    }
}
