package com.marcobrenes.githubtrending.remote

import com.marcobrenes.githubtrending.data.model.ProjectEntity
import com.marcobrenes.githubtrending.data.repository.ProjectsRemote
import com.marcobrenes.githubtrending.remote.mapper.ProjectsResponseModelMapper
import com.marcobrenes.githubtrending.remote.service.GithubTrendingService
import io.reactivex.Observable
import javax.inject.Inject



class ProjectsRemoteImpl @Inject constructor(
        private val service: GithubTrendingService,
        private val mapper: ProjectsResponseModelMapper
) : ProjectsRemote {

    companion object {
        const val QUERY = "language:kotlin"
        const val SORT_BY = "stars"
        const val ORDER = "desc"
    }

    override fun getProjects(): Observable<List<ProjectEntity>> {
        return service.searchRepositories(QUERY, SORT_BY, ORDER)
                .map {
                    it.items.map { mapper.mapFromModel(it) }
                }
    }
}
