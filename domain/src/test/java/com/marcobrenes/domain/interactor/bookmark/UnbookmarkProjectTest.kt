package com.marcobrenes.domain.interactor.bookmark

import com.marcobrenes.domain.executor.PostExecutionThread
import com.marcobrenes.domain.repository.ProjectsRepository
import com.marcobrenes.domain.test.ProjectDataFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class UnbookmarkProjectTest {

    private lateinit var unbookmarkProject: UnbookmarkProject
    @Mock lateinit var projectsRepository: ProjectsRepository
    @Mock lateinit var postExecutionThread: PostExecutionThread

    @Before fun setup() {
        MockitoAnnotations.initMocks(this)
        unbookmarkProject = UnbookmarkProject(projectsRepository, postExecutionThread)
    }

    @Test fun unbookmarkProjectCompletes() {
        stubUnbookmarkProject(Completable.complete())
        val testObserver = unbookmarkProject.buildUseCaseCompletable(
                UnbookmarkProject.Params.forProject(ProjectDataFactory.randomUuid()))
                .test()
        testObserver.assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun unbookmarkProjectsThrowsException() {
        unbookmarkProject.buildUseCaseCompletable().test()
    }

    private fun stubUnbookmarkProject(completable: Completable) {
        whenever(projectsRepository.unbookmarkProject(any())) doReturn completable
    }
}
