package com.marcobrenes.githubtrending.cache.mapper

import com.marcobrenes.githubtrending.cache.model.CachedProject
import com.marcobrenes.githubtrending.data.model.ProjectEntity

class CachedProjectMapper : CacheMapper<CachedProject, ProjectEntity> {

    override fun mapFromCached(type: CachedProject): ProjectEntity {
        return with(type) {
            ProjectEntity(id, name, fullName, starCount, dateCreated, ownerName, ownerAvatar, isBookmarked)
        }
    }

    override fun mapToCached(type: ProjectEntity): CachedProject {
        return with(type) {
           CachedProject(id, name, fullName, starCount, dateCreated, ownerName, ownerAvatar, isBookmarked)
        }
    }
}
