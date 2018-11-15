package com.marcobrenes.mobileui.injection

import com.marcobrenes.githubtrending.data.repository.ProjectsRemote
import com.marcobrenes.githubtrending.remote.service.GithubTrendingService
import com.nhaarman.mockitokotlin2.mock
import dagger.Module
import dagger.Provides

@Module
object TestRemoteModule {

    @Provides
    @JvmStatic
    fun provideGithubService(): GithubTrendingService = mock()

    @Provides
    @JvmStatic
    fun providesProjectsRemote(): ProjectsRemote = mock()

}
