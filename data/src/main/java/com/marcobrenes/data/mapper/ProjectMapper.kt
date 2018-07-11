package com.marcobrenes.data.mapper

import com.marcobrenes.data.model.ProjectEntity
import com.marcobrenes.domain.model.Project
import javax.inject.Inject

class ProjectMapper @Inject constructor(): EntityMapper<ProjectEntity, Project> {

    override fun mapFromEntity(entity: ProjectEntity): Project {
        return with(entity) {
            Project(id, name, fullName, starCount, dateCreated,
                    ownerName, ownerAvatar)
        }
    }

    override fun mapToEntity(domain: Project): ProjectEntity {
        return with(domain) {
            ProjectEntity(id, name, fullName, starCount, dateCreated,
                    ownerName, ownerAvatar)
        }
    }
}