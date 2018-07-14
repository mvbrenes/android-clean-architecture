package com.marcobrenes.githubtrending.data.mapper

import com.marcobrenes.githubtrending.data.model.ProjectEntity
import com.marcobrenes.githubtrending.domain.model.Project
import javax.inject.Inject

class ProjectMapper @Inject constructor(): EntityMapper<ProjectEntity, Project> {

    override fun mapFromEntity(entity: ProjectEntity): Project {
        return with(entity) {
            Project(id, name, fullName, starCount, dateCreated,
                    ownerName, ownerAvatar, isBookmarked)
        }
    }

    override fun mapToEntity(domain: Project): ProjectEntity {
        return with(domain) {
            ProjectEntity(id, name, fullName, starCount, dateCreated,
                    ownerName, ownerAvatar, isBookmarked)
        }
    }
}