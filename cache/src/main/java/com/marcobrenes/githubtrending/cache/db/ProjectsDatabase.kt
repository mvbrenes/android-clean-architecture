package com.marcobrenes.githubtrending.cache.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.marcobrenes.githubtrending.cache.dao.CachedProjectsDao
import com.marcobrenes.githubtrending.cache.dao.ConfigDao
import com.marcobrenes.githubtrending.cache.model.CachedProject
import com.marcobrenes.githubtrending.cache.model.Config

@Database(
        entities = [
            CachedProject::class,
            Config::class],
        version = 1,
        exportSchema = false)
abstract class ProjectsDatabase : RoomDatabase() {

    abstract fun cachedProjectsDao(): CachedProjectsDao

    abstract fun configDao(): ConfigDao

    companion object {
        private var INSTANCE: ProjectsDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context): ProjectsDatabase {
            if (INSTANCE == null) {
                synchronized(lock) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                                ProjectsDatabase::class.java, "projects.db")
                                .build()
                    }
                    return INSTANCE as ProjectsDatabase
                }
            }
            return INSTANCE as ProjectsDatabase
        }
    }
}
