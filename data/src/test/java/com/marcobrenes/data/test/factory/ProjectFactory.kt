package com.marcobrenes.data.test.factory

import com.marcobrenes.data.model.ProjectEntity
import com.marcobrenes.domain.model.Project

internal object ProjectFactory {

    fun makeProjectEntity(): ProjectEntity {
        return with(DataFactory) {
            ProjectEntity(randomString(), randomString(),
                    randomString(), randomString(),
                    randomString(), randomString(),
                    randomString(), randomBoolean())
        }
    }

    fun makeProject(): Project {
        return with(DataFactory) {
            Project(randomString(), randomString(),
                    randomString(), randomString(),
                    randomString(), randomString(),
                    randomString(), randomBoolean())
        }
    }
}