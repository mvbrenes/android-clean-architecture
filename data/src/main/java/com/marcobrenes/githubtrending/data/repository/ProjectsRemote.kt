package com.marcobrenes.githubtrending.data.repository

import com.marcobrenes.githubtrending.data.model.ProjectEntity

interface ProjectsRemote {
    suspend fun getProjects(): List<ProjectEntity>
}
