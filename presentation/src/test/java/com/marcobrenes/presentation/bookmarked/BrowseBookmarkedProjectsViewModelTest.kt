package com.marcobrenes.presentation.bookmarked

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.marcobrenes.domain.interactor.bookmark.GetBookmarkedProjects
import com.marcobrenes.domain.model.Project
import com.marcobrenes.presentation.BrowseBookmarkedProjectsViewModel
import com.marcobrenes.presentation.mapper.ProjectViewMapper
import com.marcobrenes.presentation.model.ProjectView
import com.marcobrenes.presentation.state.ResourceState
import com.marcobrenes.presentation.test.factory.DataFactory
import com.marcobrenes.presentation.test.factory.ProjectFactory
import com.nhaarman.mockitokotlin2.*
import io.reactivex.observers.DisposableObserver
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Captor
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class BrowseBookmarkedProjectsViewModelTest {

    @get:Rule var instantTaskExecutorRule = InstantTaskExecutorRule()
    var getBookmarkedProjects = mock<GetBookmarkedProjects>()
    var mapper = mock<ProjectViewMapper>()
    var projectViewModel = BrowseBookmarkedProjectsViewModel(getBookmarkedProjects, mapper)

    @Captor val captor = argumentCaptor<DisposableObserver<List<Project>>>()

    @Test fun fetchProjectsExecutesUseCase() {
        projectViewModel.fetchProjects()
        verify(getBookmarkedProjects, times(1)).execute(any(), eq(null))
    }

    @Test fun fetProjectsReturnsSuccess() {
        val projects = ProjectFactory.makeProjectList(2)
        val projectViews = ProjectFactory.makeProjectViewList(2)
        stubProjectMapperMapToView(projects[0], projectViews[0])
        stubProjectMapperMapToView(projects[1], projectViews[1])
        projectViewModel.fetchProjects()
        verify(getBookmarkedProjects).execute(captor.capture(), eq(null))
        captor.firstValue.onNext(projects)
        assertEquals(ResourceState.SUCCESS, projectViewModel.getProjects().value?.status)
    }

    @Test fun fetProjectsReturnsData() {
        val projects = ProjectFactory.makeProjectList(2)
        val projectViews = ProjectFactory.makeProjectViewList(2)
        stubProjectMapperMapToView(projects[0], projectViews[0])
        stubProjectMapperMapToView(projects[1], projectViews[1])
        projectViewModel.fetchProjects()
        verify(getBookmarkedProjects).execute(captor.capture(), eq(null))
        captor.firstValue.onNext(projects)
        assertEquals(projectViews, projectViewModel.getProjects().value?.data)
    }

    @Test fun fetProjectsReturnsError() {
        projectViewModel.fetchProjects()
        verify(getBookmarkedProjects).execute(captor.capture(), eq(null))
        captor.firstValue.onError(RuntimeException())
        assertEquals(ResourceState.ERROR, projectViewModel.getProjects().value?.status)
    }

    @Test fun fetProjectsReturnsMessageForError() {
        val errorMessage = DataFactory.randomString()
        projectViewModel.fetchProjects()
        verify(getBookmarkedProjects).execute(captor.capture(), eq(null))
        captor.firstValue.onError(RuntimeException(errorMessage))
        assertEquals(errorMessage,  projectViewModel.getProjects().value?.message)
    }

    private fun stubProjectMapperMapToView(project: Project, projectView: ProjectView) {
        whenever(mapper.mapToView(project)) doReturn projectView
    }
}
