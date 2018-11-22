package com.marcobrenes.githubtrending.cache.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.marcobrenes.githubtrending.cache.db.ProjectsDatabase
import com.marcobrenes.githubtrending.cache.test.factory.ConfigDataFactory
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ConfigDaoTest {

    @Rule
    @JvmField var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ProjectsDatabase::class.java)
            .allowMainThreadQueries()
            .build()

    @After fun clearDb() {
        database.close()
    }

    @Test fun saveConfiguration() {
        val config = ConfigDataFactory.makeCachedConfig()
        database.configDao().insertConfig(config)
        val testObserver = database.configDao().getConfig().test()
        testObserver.assertValue(config)
    }
}
