package com.marcobrenes.githubtrending.domain.interactor.bookmark

import com.marcobrenes.githubtrending.domain.repository.ProjectsRepository
import javax.inject.Inject

class BookmarkProject @Inject constructor(private val projectsRepository: ProjectsRepository) {
    suspend operator fun invoke(projectId: String) {
        projectsRepository.bookmarkProject(projectId)
    }
}