package com.marcobrenes.githubtrending.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marcobrenes.githubtrending.domain.interactor.bookmark.BookmarkProject
import com.marcobrenes.githubtrending.domain.interactor.bookmark.UnbookmarkProject
import com.marcobrenes.githubtrending.domain.interactor.browse.GetProjects
import com.marcobrenes.githubtrending.domain.model.Project
import com.marcobrenes.githubtrending.presentation.mapper.ProjectViewMapper
import com.marcobrenes.githubtrending.presentation.model.ProjectView
import com.marcobrenes.githubtrending.presentation.state.Resource
import com.marcobrenes.githubtrending.presentation.state.ResourceState
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class BrowseProjectsViewModel @Inject constructor(
        private val getProjects: GetProjects?,
        private val bookmarkProject: BookmarkProject,
        private val unbookmarkProject: UnbookmarkProject,
        private val mapper: ProjectViewMapper): ViewModel() {

    private val liveData: MutableLiveData<Resource<List<ProjectView>>> = MutableLiveData()

    override fun onCleared() {
        getProjects?.dispose()
        super.onCleared()
    }

    fun getProjects(): LiveData<Resource<List<ProjectView>>> {
        return liveData
    }

    fun fetchProjects() {
        liveData.postValue(Resource(ResourceState.LOADING))
        getProjects?.execute(ProjectsSubscriber())
    }

    fun bookmarkProject(projectId: String) {
        liveData.postValue(Resource(ResourceState.LOADING))
        bookmarkProject.execute(
                BookmarkProjectsSubscriber(),
                BookmarkProject.Params.forProject(projectId))
    }

    fun unBookmarkProject(projectId: String) {
        liveData.postValue(Resource(ResourceState.LOADING))
        unbookmarkProject.execute(
                BookmarkProjectsSubscriber(),
                UnbookmarkProject.Params.forProject(projectId))
    }

    inner class ProjectsSubscriber : DisposableObserver<List<Project>>() {
        override fun onComplete() {}

        override fun onNext(t: List<Project>) {
            liveData.postValue(Resource(
                    ResourceState.SUCCESS,
                    t.map { mapper.mapToView(it) }))
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
                    ResourceState.SUCCESS))
        }

        override fun onError(e: Throwable) {
            liveData.postValue(Resource(
                    ResourceState.ERROR,
                    message = e.localizedMessage))
        }
    }
}