package com.marcobrenes.mobileui.mapper

import com.marcobrenes.githubtrending.presentation.model.ProjectView
import com.marcobrenes.mobileui.model.Project
import javax.inject.Inject

class ProjectViewMapper @Inject constructor() : ViewMapper<ProjectView, Project> {
    override fun mapToView(presentation: ProjectView): Project {
        return with(presentation) {
            Project(id, name, fullName, starCount, dateCreated, ownerName, ownerAvatar, isBookmarked)
        }
    }
}