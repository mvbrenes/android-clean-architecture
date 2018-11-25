package com.marcobrenes.mobileui.injection

import android.app.Application
import com.marcobrenes.githubtrending.cache.db.ProjectsDatabase
import com.marcobrenes.githubtrending.data.repository.ProjectsCache
import com.nhaarman.mockitokotlin2.mock
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object TestCacheModule {

    @Provides @JvmStatic @Singleton
    fun providesDatabase(application: Application): ProjectsDatabase {
        return ProjectsDatabase.getInstance(application)
    }

    @Provides @JvmStatic @Singleton
    fun provideProjectsCache(): ProjectsCache = mock()
}
