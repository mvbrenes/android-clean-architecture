package com.marcobrenes.domain.repository

import com.marcobrenes.domain.model.Project
import io.reactivex.Completable
import io.reactivex.Observable

interface ProjectsRepository {
    fun getProjects(): Observable<List<Project>>
    fun bookmarkProject(projectId: String): Completable
    fun getBookmarkedProjects(): Observable<List<Project>>
}