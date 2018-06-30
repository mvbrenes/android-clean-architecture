package com.marcobrenes.domain.interactor.browse

import com.marcobrenes.domain.executor.PostExecutionThread
import com.marcobrenes.domain.interactor.ObservableUseCase
import com.marcobrenes.domain.model.Project
import com.marcobrenes.domain.repository.ProjectsRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetProjects @Inject constructor(
        private val projectsRepository: ProjectsRepository,
        postExecutionThread: PostExecutionThread)
    : ObservableUseCase<List<Project>, Nothing>(postExecutionThread) {

    override fun buildUseCaseObservable(params: Nothing?): Observable<List<Project>> {
        return projectsRepository.getProjects()
    }
}