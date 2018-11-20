package com.marcobrenes.githubtrending.domain.interactor.bookmark

import com.marcobrenes.githubtrending.domain.executor.PostExecutionThread
import com.marcobrenes.githubtrending.domain.model.Project
import com.marcobrenes.githubtrending.domain.repository.ProjectsRepository
import com.marcobrenes.githubtrending.domain.test.ProjectDataFactory
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test

class GetBookmarkedProjectsTest {
    private lateinit var getBookmarkProjects: GetBookmarkedProjects
    private val projectsRepository: ProjectsRepository = mock()
    private val postExecutionThread: PostExecutionThread = mock()

    @Before fun setup() {
        getBookmarkProjects = GetBookmarkedProjects(projectsRepository, postExecutionThread)
    }

    @Test fun getBookmarkedProjectsComplete() {
        stubGetProjects(Observable.just(ProjectDataFactory.makeProjectList(2)))
        val testObserver = getBookmarkProjects.buildUseCaseObservable().test()
        testObserver.assertComplete()
    }

    @Test fun getBookmarkedProjectsReturnsData() {
        val projects = ProjectDataFactory.makeProjectList(2)
        stubGetProjects(Observable.just(projects))
        val testObserver = getBookmarkProjects.buildUseCaseObservable().test()
        testObserver.assertValue(projects)
    }

    private fun stubGetProjects(observable: Observable<List<Project>>) {
        whenever(projectsRepository.getBookmarkedProjects())
                .doReturn(observable)
    }
}