package com.marcobrenes.githubtrending.data.repository

import com.marcobrenes.githubtrending.data.model.ProjectEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable

interface ProjectsDataSource {

    fun getProjects(): Flowable<List<ProjectEntity>>

    fun saveProjects(projects: List<ProjectEntity>): Completable

    fun clearProjects(): Completable

    fun getBookmarkedProjects(): Observable<List<ProjectEntity>>

    fun setProjectAsBookmarked(projectId: String): Completable

    fun setProjectAsNotBookmarked(projectId: String): Completable
}