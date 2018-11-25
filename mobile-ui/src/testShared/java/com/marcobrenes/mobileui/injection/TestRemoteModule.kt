package com.marcobrenes.mobileui.injection

import com.marcobrenes.githubtrending.data.repository.ProjectsRemote
import com.marcobrenes.githubtrending.remote.service.GithubTrendingService
import com.nhaarman.mockitokotlin2.mock
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object TestRemoteModule {

    @Provides @JvmStatic @Singleton
    fun provideGithubService(): GithubTrendingService = mock()

    @Provides @JvmStatic @Singleton
    fun providesProjectsRemote(): ProjectsRemote = mock()

}
