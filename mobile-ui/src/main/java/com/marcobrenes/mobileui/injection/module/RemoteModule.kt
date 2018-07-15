package com.marcobrenes.mobileui.injection.module

import com.marcobrenes.githubtrending.data.repository.ProjectsRemote
import com.marcobrenes.githubtrending.remote.ProjectsRemoteImpl
import com.marcobrenes.githubtrending.remote.service.GithubTrendingService
import com.marcobrenes.githubtrending.remote.service.GithubTrendingServiceFactory
import com.marcobrenes.mobileui.BuildConfig
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class RemoteModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideGithubService(): GithubTrendingService {
            return GithubTrendingServiceFactory.makeGithubTrendingService(BuildConfig.DEBUG)
        }
    }

    @Binds
    abstract fun bindProjectsRemote(projectsRemote: ProjectsRemoteImpl): ProjectsRemote

}
