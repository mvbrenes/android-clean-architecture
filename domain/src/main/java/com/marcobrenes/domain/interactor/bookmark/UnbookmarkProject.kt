package com.marcobrenes.domain.interactor.bookmark

import com.marcobrenes.domain.executor.PostExecutionThread
import com.marcobrenes.domain.interactor.CompletableUseCase
import com.marcobrenes.domain.repository.ProjectsRepository
import io.reactivex.Completable
import javax.inject.Inject

class UnbookmarkProject @Inject constructor(
        private val projectsRepository: ProjectsRepository,
        postExecutionThread: PostExecutionThread)
    : CompletableUseCase<UnbookmarkProject.Params>(postExecutionThread) {

    override fun buildUseCaseCompletable(params: Params?): Completable {
        if (params == null) throw IllegalArgumentException("Params can't be null!")
        return projectsRepository.bookmarkProject(params.projectId)
    }

    data class Params constructor(val projectId: String) {
        companion object {
            fun forProject(projectId: String): Params {
                return Params(projectId)
            }
        }
    }
}