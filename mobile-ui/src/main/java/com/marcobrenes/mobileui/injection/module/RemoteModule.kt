package com.marcobrenes.mobileui.injection.module

import com.marcobrenes.githubtrending.data.repository.ProjectsRemote
import com.marcobrenes.githubtrending.remote.ProjectsRemoteImpl
import com.marcobrenes.githubtrending.remote.service.GithubTrendingService
import com.marcobrenes.githubtrending.remote.service.GithubTrendingServiceFactory
import com.marcobrenes.mobileui.BuildConfig
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RemoteModule {

    @Provides
    @Singleton
    fun provideGithubService(): GithubTrendingService {
        return GithubTrendingServiceFactory.makeGithubTrendingService(BuildConfig.DEBUG)
    }

    @Provides
    fun bindProjectsRemote(projectsRemote: ProjectsRemoteImpl): ProjectsRemote = projectsRemote

}
