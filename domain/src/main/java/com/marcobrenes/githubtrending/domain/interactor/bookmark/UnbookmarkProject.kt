package com.marcobrenes.githubtrending.domain.interactor.bookmark

import com.marcobrenes.githubtrending.domain.repository.ProjectsRepository
import javax.inject.Inject

class UnbookmarkProject @Inject constructor(private val projectsRepository: ProjectsRepository) {
    suspend operator fun invoke(projectId: String) {
        projectsRepository.unbookmarkProject(projectId)
    }
}