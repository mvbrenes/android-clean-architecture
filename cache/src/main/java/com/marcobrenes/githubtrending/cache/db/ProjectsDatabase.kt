package com.marcobrenes.githubtrending.cache.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.marcobrenes.githubtrending.cache.dao.CachedProjectsDao
import com.marcobrenes.githubtrending.cache.dao.ConfigDao
import com.marcobrenes.githubtrending.cache.model.CachedProject
import com.marcobrenes.githubtrending.cache.model.Config
import com.marcobrenes.githubtrending.cache.model.SingletonHolder

@Database(
        entities = [
            CachedProject::class,
            Config::class],
        version = 1,
        exportSchema = false)
abstract class ProjectsDatabase : RoomDatabase() {

    abstract fun cachedProjectsDao(): CachedProjectsDao

    abstract fun configDao(): ConfigDao

    companion object : SingletonHolder<ProjectsDatabase, Context>({
        Room.databaseBuilder(
                it.applicationContext,
                ProjectsDatabase::class.java,
                "projects.db"
        ).build()
    })
}
