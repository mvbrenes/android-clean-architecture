package com.marcobrenes.githubtrending.presentation.bookmarked

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.marcobrenes.githubtrending.domain.interactor.CoroutineCallback
import com.marcobrenes.githubtrending.domain.interactor.bookmark.GetBookmarkedProjects
import com.marcobrenes.githubtrending.domain.model.Project
import com.marcobrenes.githubtrending.presentation.BrowseBookmarkedProjectsViewModel
import com.marcobrenes.githubtrending.presentation.mapper.ProjectViewMapper
import com.marcobrenes.githubtrending.presentation.model.ProjectView
import com.marcobrenes.githubtrending.presentation.state.ResourceState
import com.marcobrenes.githubtrending.presentation.test.factory.DataFactory
import com.marcobrenes.githubtrending.presentation.test.factory.ProjectFactory
import com.nhaarman.mockitokotlin2.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class BrowseBookmarkedProjectsViewModelTest {

    @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val getBookmarkedProjects: GetBookmarkedProjects = mock()
    private val mapper: ProjectViewMapper = mock()
    private val projectViewModel = BrowseBookmarkedProjectsViewModel(getBookmarkedProjects, mapper)
    private val captor = argumentCaptor<CoroutineCallback<List<Project>>>()

    @Test fun fetchProjectsExecutesUseCase() {
        projectViewModel.fetchProjects()
        verify(getBookmarkedProjects, times(1)).invoke(eq(null), any())
    }

    @Test fun fetProjectsReturnsSuccess() {
        val projects = ProjectFactory.makeProjectList(2)
        val projectViews = ProjectFactory.makeProjectViewList(2)
        stubProjectMapperMapToView(projects[0], projectViews[0])
        stubProjectMapperMapToView(projects[1], projectViews[1])
        projectViewModel.fetchProjects()
        verify(getBookmarkedProjects).invoke(eq(null), captor.capture())
        captor.firstValue.sourceLoaded(projects)
        assertEquals(ResourceState.SUCCESS, projectViewModel.getProjects().value?.status)
    }

    @Test fun fetProjectsReturnsData() {
        val projects = ProjectFactory.makeProjectList(2)
        val projectViews = ProjectFactory.makeProjectViewList(2)
        stubProjectMapperMapToView(projects[0], projectViews[0])
        stubProjectMapperMapToView(projects[1], projectViews[1])
        projectViewModel.fetchProjects()
        verify(getBookmarkedProjects).invoke(eq(null), captor.capture())
        captor.firstValue.sourceLoaded(projects)
        assertEquals(projectViews, projectViewModel.getProjects().value?.data)
    }

    @Test fun fetProjectsReturnsError() {
        projectViewModel.fetchProjects()
        verify(getBookmarkedProjects).invoke(eq(null), captor.capture())
        captor.firstValue.loadFailed(RuntimeException())
        assertEquals(ResourceState.ERROR, projectViewModel.getProjects().value?.status)
    }

    @Test fun fetProjectsReturnsMessageForError() {
        val errorMessage = DataFactory.randomString()
        projectViewModel.fetchProjects()
        verify(getBookmarkedProjects).invoke(eq(null), captor.capture())
        captor.firstValue.loadFailed(RuntimeException(errorMessage))
        assertEquals(errorMessage, projectViewModel.getProjects().value?.message)
    }

    private fun stubProjectMapperMapToView(project: Project, projectView: ProjectView) {
        whenever(mapper.mapToView(project)) doReturn projectView
    }
}
