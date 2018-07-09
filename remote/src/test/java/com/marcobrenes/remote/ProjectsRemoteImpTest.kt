package com.marcobrenes.remote

import com.marcobrenes.data.model.ProjectEntity
import com.marcobrenes.remote.ProjectsRemoteImpl.Companion.ORDER
import com.marcobrenes.remote.ProjectsRemoteImpl.Companion.QUERY
import com.marcobrenes.remote.ProjectsRemoteImpl.Companion.SORT_BY
import com.marcobrenes.remote.mapper.ProjectsResponseModelMapper
import com.marcobrenes.remote.model.ProjectModel
import com.marcobrenes.remote.model.ProjectsResponseModel
import com.marcobrenes.remote.service.GithubTrendingService
import com.marcobrenes.remote.test.factory.ProjectDataFactory
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Observable
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ProjectsRemoteImpTest {

    private val mapper = mock<ProjectsResponseModelMapper>()
    private val service = mock<GithubTrendingService>()
    private val remote = ProjectsRemoteImpl(service, mapper)

    @Test fun getProjectsCompletes() {
        stubGithubTrendingServiceSearchRepositories(Observable.just(
                ProjectDataFactory.makeProjectsResponse()))
        stubProjectsResponseModelMapperFromModel(any(), ProjectDataFactory.makeProjectEntity())

        val testObserver = remote.getProjects().test()
        testObserver.assertComplete()
    }

    @Test fun getProjectsCallServer() {
        stubGithubTrendingServiceSearchRepositories(Observable.just(
                ProjectDataFactory.makeProjectsResponse()))
        stubProjectsResponseModelMapperFromModel(any(), ProjectDataFactory.makeProjectEntity())

        remote.getProjects().test()
        verify(service).searchRepositories(any(), any(), any())
    }

    @Test fun getProjectsReturnsData() {
        val response = ProjectDataFactory.makeProjectsResponse()
        stubGithubTrendingServiceSearchRepositories(Observable.just(response))
        val entities = mutableListOf<ProjectEntity>()
        response.items.forEach {
            val entity = ProjectDataFactory.makeProjectEntity()
            entities.add(entity)
            stubProjectsResponseModelMapperFromModel(it, entity)
        }
        val testObserver = remote.getProjects().test()
        testObserver.assertValue(entities)
    }

    @Test fun getProjectsCallsWithCorrectParameter() {
        stubGithubTrendingServiceSearchRepositories(Observable.just(
                ProjectDataFactory.makeProjectsResponse()))
        stubProjectsResponseModelMapperFromModel(any(), ProjectDataFactory.makeProjectEntity())

        remote.getProjects().test()
        verify(service).searchRepositories(QUERY, SORT_BY, ORDER)
    }

    private fun stubGithubTrendingServiceSearchRepositories(observable: Observable<ProjectsResponseModel>) {
        whenever(service.searchRepositories(any(), any(), any())) doReturn observable
    }

    private fun stubProjectsResponseModelMapperFromModel(model: ProjectModel, entity: ProjectEntity) {
        whenever(mapper.mapFromModel(model)) doReturn entity
    }
}