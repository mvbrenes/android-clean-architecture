package com.marcobrenes.mobileui.test.factory

import com.marcobrenes.githubtrending.presentation.model.ProjectView

object ProjectFactory {

    fun makeProjectView(): ProjectView = with(DataFactory) {
        ProjectView(
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
