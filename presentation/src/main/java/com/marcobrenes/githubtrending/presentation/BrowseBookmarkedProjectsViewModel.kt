package com.marcobrenes.githubtrending.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marcobrenes.githubtrending.domain.interactor.CoroutineCallback
import com.marcobrenes.githubtrending.domain.interactor.bookmark.GetBookmarkedProjects
import com.marcobrenes.githubtrending.domain.model.Project
import com.marcobrenes.githubtrending.presentation.mapper.ProjectViewMapper
import com.marcobrenes.githubtrending.presentation.model.ProjectView
import com.marcobrenes.githubtrending.presentation.state.Resource
import com.marcobrenes.githubtrending.presentation.state.ResourceState
import javax.inject.Inject

class BrowseBookmarkedProjectsViewModel @Inject constructor(
    private val getBookmarkedProjects: GetBookmarkedProjects,
    private val mapper: ProjectViewMapper
) : ViewModel(), CoroutineCallback<List<Project>> {


    private val liveData: MutableLiveData<Resource<List<ProjectView>>> = MutableLiveData()

    override fun onCleared() {
        getBookmarkedProjects.dispose()
        super.onCleared()
    }

    fun getProjects(): LiveData<Resource<List<ProjectView>>> {
        return liveData
    }

    fun fetchProjects() {
        liveData.postValue(Resource(ResourceState.LOADING))
        getBookmarkedProjects(callback = this)
    }

    override fun sourceLoaded(result: List<Project>) {
        liveData.postValue(
            Resource(
                ResourceState.SUCCESS,
                data = result.map { mapper.mapToView(it) })
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
