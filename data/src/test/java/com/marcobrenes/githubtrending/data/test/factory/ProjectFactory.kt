package com.marcobrenes.githubtrending.data.test.factory

import com.marcobrenes.githubtrending.data.model.ProjectEntity
import com.marcobrenes.githubtrending.domain.model.Project

internal object ProjectFactory {

    fun makeProjectEntity(): ProjectEntity {
        return with(DataFactory) {
            ProjectEntity(
                    randomString(),
                    randomString(),
                    randomString(),
                    randomString(),
                    randomString(),
                    randomString(),
                    randomString(),
                    randomBoolean()
            )
        }
    }

    fun makeProject(): Project {
        return with(DataFactory) {
            Project(
                    randomString(),
                    randomString(),
                    randomString(),
                    randomString(),
                    randomString(),
                    randomString(),
                    randomString(),
                    randomBoolean()
            )
        }
    }
}