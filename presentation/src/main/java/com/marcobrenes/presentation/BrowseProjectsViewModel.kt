package com.marcobrenes.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marcobrenes.domain.interactor.bookmark.BookmarkProject
import com.marcobrenes.domain.interactor.bookmark.UnbookmarkProject
import com.marcobrenes.domain.interactor.browse.GetProjects
import com.marcobrenes.domain.model.Project
import com.marcobrenes.presentation.mapper.ProjectViewMapper
import com.marcobrenes.presentation.model.ProjectView
import com.marcobrenes.presentation.state.Resource
import com.marcobrenes.presentation.state.ResourceState
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class BrowseProjectsViewModel @Inject constructor(
        private val getProjects: GetProjects,
        private val bookmarkProject: BookmarkProject,
        private val unbookmarkProject: UnbookmarkProject,
        private val mapper: ProjectViewMapper): ViewModel() {

    private val liveData: MutableLiveData<Resource<List<ProjectView>>> = MutableLiveData()

    override fun onCleared() {
        getProjects.dispose()
        super.onCleared()
    }

    fun getProjects(): LiveData<Resource<List<ProjectView>>> {
        return liveData
    }

    fun fetchProjects() {
        liveData.postValue(Resource(ResourceState.LOADING))
        return getProjects.execute(ProjectsSubscriber())
    }

    fun bookmarkProject(projectId: String) {
        liveData.postValue(Resource(ResourceState.LOADING))
        return bookmarkProject.execute(
                BookmarkProjectsSubscriber(),
                BookmarkProject.Params.forProject(projectId))
    }

    fun unBookmarkProject(projectId: String) {
        liveData.postValue(Resource(ResourceState.LOADING))
        return unbookmarkProject.execute(
                BookmarkProjectsSubscriber(),
                UnbookmarkProject.Params.forProject(projectId))
    }

    inner class ProjectsSubscriber : DisposableObserver<List<Project>>() {
        override fun onComplete() {}

        override fun onNext(t: List<Project>) {
            liveData.postValue(Resource(
                    ResourceState.SUCCESS,
                    t.map { mapper.mapToView(it) },
                    null))
        }

        override fun onError(e: Throwable) {
            liveData.postValue(Resource(
                    ResourceState.ERROR,
                    message = e.localizedMessage))
        }
    }

    inner class BookmarkProjectsSubscriber : DisposableCompletableObserver() {
        override fun onComplete() {
            liveData.postValue(Resource(
                    ResourceState.SUCCESS,
                    data = liveData.value?.data))
        }

        override fun onError(e: Throwable) {
            liveData.postValue(Resource(
                    ResourceState.ERROR,
                    data = liveData.value?.data,
                    message = e.localizedMessage))
        }
    }
}