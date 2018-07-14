package com.marcobrenes.githubtrending.remote.test.factory

import com.marcobrenes.githubtrending.data.model.ProjectEntity
import com.marcobrenes.githubtrending.remote.model.OwnerModel
import com.marcobrenes.githubtrending.remote.model.ProjectModel
import com.marcobrenes.githubtrending.remote.model.ProjectsResponseModel

object ProjectDataFactory {

    fun makeOwner(): OwnerModel {
        return with(DataFactory) {
            OwnerModel(randomString(), randomString())
        }
    }

    fun makeProject(): ProjectModel {
        return with(DataFactory) {
            ProjectModel(randomString(), randomString(), randomString(),
                    randomInt(), randomString(), makeOwner())
        }
    }

    fun makeProjectEntity(): ProjectEntity {
        return with(DataFactory) {
            ProjectEntity(randomString(), randomString(), randomString(),
                    randomString(), randomString(), randomString(), randomString(), randomBoolean())
        }
    }

    fun makeProjectsResponse(): ProjectsResponseModel {
        return ProjectsResponseModel(listOf(makeProject(), makeProject()))
    }
}
