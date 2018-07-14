package com.marcobrenes.presentation.mapper

import com.marcobrenes.domain.model.Project
import com.marcobrenes.presentation.model.ProjectView
import javax.inject.Inject

class ProjectViewMapper @Inject constructor(): Mapper<ProjectView, Project> {
    override fun mapToView(type: Project): ProjectView {
        return with(type) {
            ProjectView(
                    id,
                    name,
                    fullName,
                    starCount,
                    dateCreated,
                    ownerName,
                    ownerAvatar,
                    isBookmarked
            )
        }
    }
}
