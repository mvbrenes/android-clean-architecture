package com.marcobrenes.githubtrending.domain.interactor.bookmark

import com.marcobrenes.githubtrending.domain.executor.BaseDispatcherProvider
import com.marcobrenes.githubtrending.domain.interactor.CoroutineChannelUseCase
import com.marcobrenes.githubtrending.domain.model.Project
import com.marcobrenes.githubtrending.domain.repository.ProjectsRepository
import kotlinx.coroutines.channels.ReceiveChannel
import javax.inject.Inject

class GetBookmarkedProjects @Inject constructor(
    private val projectsRepository: ProjectsRepository,
    dispatcherProvider: BaseDispatcherProvider
) : CoroutineChannelUseCase<List<Project>, Nothing?>(
    subscribeDispatcher = dispatcherProvider.io,
    observeDispatcher = dispatcherProvider.main
) {
    override suspend fun getChannel(params: Nothing?): ReceiveChannel<List<Project>> {
        return projectsRepository.getBookmarkedProjects()
    }
}

