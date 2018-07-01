package com.marcobrenes.domain.interactor.browse

import com.marcobrenes.domain.executor.PostExecutionThread
import com.marcobrenes.domain.interactor.browse.GetProjects
import com.marcobrenes.domain.model.Project
import com.marcobrenes.domain.repository.ProjectsRepository
import com.marcobrenes.domain.test.ProjectDataFactory
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetProjectsTest {

    private lateinit var getProjects: GetProjects
    @Mock private lateinit var projectsRepository: ProjectsRepository
    @Mock private lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getProjects = GetProjects(projectsRepository, postExecutionThread)
    }

    @Test
    fun getProjectsCompletes() {
        stubGetProjects(Observable.just(ProjectDataFactory.makeProjectList(2)))
        val testObserver = getProjects.buildUseCaseObservable().test()
        testObserver.assertComplete()
    }

    @Test
    fun getProjectsReturnsData() {
        val projects = ProjectDataFactory.makeProjectList(2)
        stubGetProjects(Observable.just(projects))
        val testObserver = getProjects.buildUseCaseObservable().test()
        testObserver.assertValue(projects)
    }

    private fun stubGetProjects(observable: Observable<List<Project>>) {
        whenever(projectsRepository.getProjects()).doReturn(observable)
    }
}