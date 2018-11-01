package com.marcobrenes.mobileui.injection.module

import android.content.Context
import com.marcobrenes.githubtrending.cache.ProjectsCacheImpl
import com.marcobrenes.githubtrending.cache.db.ProjectsDatabase
import com.marcobrenes.githubtrending.data.repository.ProjectsCache
import dagger.Module
import dagger.Provides

@Module
class CacheModule {

    @Provides
    fun providesProjectsDatabase(context: Context): ProjectsDatabase {
        return ProjectsDatabase.getInstance(context)
    }

    @Provides
    fun providesProjectsCache(projectsCache: ProjectsCacheImpl): ProjectsCache = projectsCache
}
