package com.marcobrenes.githubtrending.remote.mapper

import com.marcobrenes.githubtrending.data.model.ProjectEntity
import com.marcobrenes.githubtrending.remote.model.ProjectModel

class ProjectsResponseModelMapper : ModelMapper<ProjectModel, ProjectEntity> {

    override fun mapFromModel(model: ProjectModel): ProjectEntity {
        return with(model) {
            ProjectEntity(id, name, fullName, starCount.toString(),
                    dateCreated, owner.ownerName, owner.ownerAvatar, false)
        }
    }
}
