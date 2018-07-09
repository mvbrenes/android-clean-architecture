package com.marcobrenes.remote.test.factory

import com.marcobrenes.data.model.ProjectEntity
import com.marcobrenes.remote.model.OwnerModel
import com.marcobrenes.remote.model.ProjectModel
import com.marcobrenes.remote.model.ProjectsResponseModel

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
                    randomString(), randomString(), randomString(), randomString())
        }
    }

    fun makeProjectsResponse(): ProjectsResponseModel {
        return ProjectsResponseModel(listOf(makeProject(), makeProject()))
    }
}
