package com.marcobrenes.githubtrending.data.repository

import com.marcobrenes.githubtrending.data.model.ProjectEntity
import io.reactivex.Observable

interface ProjectsRemote {

    fun getProjects(): Observable<List<ProjectEntity>>
}
