package com.marcobrenes.githubtrending.data.source

import com.marcobrenes.githubtrending.data.model.ProjectEntity
import com.marcobrenes.githubtrending.data.repository.ProjectsRemote
import javax.inject.Inject

class ProjectsRemoteDataSource @Inject constructor(
        private val projectsRemote: ProjectsRemote
) {
    suspend fun getProjects(): List<ProjectEntity> {
        return projectsRemote.getProjects()
    }
}