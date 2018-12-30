package com.marcobrenes.githubtrending.domain.repository

import com.marcobrenes.githubtrending.domain.model.Project
import io.reactivex.Completable
import io.reactivex.Observable
import kotlinx.coroutines.channels.ReceiveChannel

interface ProjectsRepository {
    suspend fun getProjects(): ReceiveChannel<List<Project>>
    suspend fun bookmarkProject(projectId: String)
    suspend fun unbookmarkProject(projectId: String)
    suspend fun getBookmarkedProjects(): ReceiveChannel<List<Project>>
}