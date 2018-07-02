package com.marcobrenes.data.repository

import com.marcobrenes.data.model.ProjectEntity
import io.reactivex.Observable

interface ProjectsRemote {

    fun getProjects(): Observable<List<ProjectEntity>>
}
