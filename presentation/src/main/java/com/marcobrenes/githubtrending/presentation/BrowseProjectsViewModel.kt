package com.marcobrenes.githubtrending.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marcobrenes.githubtrending.domain.executor.BaseDispatcherProvider
import com.marcobrenes.githubtrending.domain.interactor.CoroutineCallback
import com.marcobrenes.githubtrending.domain.interactor.bookmark.BookmarkProject
import com.marcobrenes.githubtrending.domain.interactor.bookmark.UnbookmarkProject
import com.marcobrenes.githubtrending.domain.interactor.browse.GetProjects
import com.marcobrenes.githubtrending.domain.model.Project
import com.marcobrenes.githubtrending.presentation.mapper.ProjectViewMapper
import com.marcobrenes.githubtrending.presentation.model.ProjectView
import com.marcobrenes.githubtrending.presentation.state.Resource
import com.marcobrenes.githubtrending.presentation.state.ResourceState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class BrowseProjectsViewModel @Inject constructor(
    private val getProjects: GetProjects,
    private val bookmarkProjectUseCase: BookmarkProject,
    private val unbookmarkProjectUseCase: UnbookmarkProject,
    private val dispatcherProvider: BaseDispatcherProvider,
    private val mapper: ProjectViewMapper
) : ViewModel(), CoroutineCallback<List<Project>> {

    private val liveData: MutableLiveData<Resource<List<ProjectView>>> = MutableLiveData()
    private val parentJob = Job()
    private val scope = CoroutineScope(dispatcherProvider.main + parentJob)

    override fun onCleared() {
        getProjects.dispose()
        parentJob.cancel()
        super.onCleared()
    }

    fun getProjects(): LiveData<Resource<List<ProjectView>>> {
        return liveData
    }

    fun fetchProjects() {
        liveData.postValue(Resource(ResourceState.LOADING))
        getProjects(callback = this)
    }

    fun bookmarkProject(projectId: String) = scope.launch(dispatcherProvider.io) {
        liveData.postValue(Resource(ResourceState.LOADING))
        try {
            bookmarkProjectUseCase(projectId)
            liveData.postValue(Resource(ResourceState.SUCCESS))
        } catch (e: Exception) {
            liveData.postValue(
                Resource(
                    ResourceState.ERROR,
                    message = e.localizedMessage
                )
            )
        }
    }

    fun unBookmarkProject(projectId: String) = scope.launch(dispatcherProvider.io) {
        liveData.postValue(Resource(ResourceState.LOADING))
        try {
            unbookmarkProjectUseCase(projectId)
            liveData.postValue(Resource(ResourceState.SUCCESS))
        } catch (e: Exception) {
            liveData.postValue(
                Resource(
                    ResourceState.ERROR,
                    message = e.localizedMessage
                )
            )
        }
    }

    override fun sourceLoaded(result: List<Project>) {
        liveData.postValue(
            Resource(
                ResourceState.SUCCESS,
                result.map { mapper.mapToView(it) })
        )
    }

    override fun loadFailed(exception: Exception) {
        liveData.postValue(
            Resource(
                ResourceState.ERROR,
                message = exception.localizedMessage
            )
        )
    }
}