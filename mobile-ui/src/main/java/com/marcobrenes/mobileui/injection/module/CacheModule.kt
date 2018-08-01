package com.marcobrenes.mobileui.injection.module

import android.content.Context
import androidx.room.Room
import com.marcobrenes.githubtrending.cache.ProjectsCacheImpl
import com.marcobrenes.githubtrending.cache.db.ProjectsDatabase
import com.marcobrenes.githubtrending.data.repository.ProjectsCache
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheModule {

    @Provides
    @Singleton
    fun providesProjectsDatabase(context: Context): ProjectsDatabase {
        return Room.databaseBuilder(context,
                ProjectsDatabase::class.java, "projects.db")
                .build()
    }

    @Provides
    fun providesProjectsCache(projectsCache: ProjectsCacheImpl): ProjectsCache = projectsCache
}
