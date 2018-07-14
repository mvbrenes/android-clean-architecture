package com.marcobrenes.githubtrending.cache

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import com.marcobrenes.githubtrending.cache.db.ProjectsDatabase
import com.marcobrenes.githubtrending.cache.mapper.CachedProjectMapper
import com.marcobrenes.githubtrending.cache.test.factory.ProjectDataFactory
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class ProjectsCacheImplTest {

    @get:Rule var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val database = Room.inMemoryDatabaseBuilder(
            RuntimeEnvironment.application.applicationContext,
            ProjectsDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    private val entityMapper = CachedProjectMapper()
    private val cache = ProjectsCacheImpl(database, entityMapper)

    @After fun clearDb() {
        database.close()
    }

    @Test fun clearTablesCompletes() {
        val testObserver = cache.clearProjects().test()
        testObserver.assertComplete()
    }

    @Test fun saveProjectsCompletes() {
        val projects = listOf(ProjectDataFactory.makeProjectEntity())
        val testObserver = cache.saveProjects(projects).test()
        testObserver.assertComplete()
    }

    @Test fun getProjectsReturnsData() {
        val projects = listOf(ProjectDataFactory.makeProjectEntity())
        cache.saveProjects(projects).test()
        val testObserver = cache.getProjects().test()
        testObserver.assertValue(projects)
    }

    @Test fun getBookmarkedProjectsReturnsData() {
        val bookmarkedProject = ProjectDataFactory.makeBookmarkedProjectEntity()
        val projects = listOf(ProjectDataFactory.makeProjectEntity(),
                bookmarkedProject)
        cache.saveProjects(projects).test()
        val testObserver = cache.getBookmarkedProjects().test()
        testObserver.assertValue(listOf(bookmarkedProject))
    }

    @Test fun setProjectAsBookmarkedCompletes() {
        val projects = listOf(ProjectDataFactory.makeProjectEntity())
        cache.saveProjects(projects).test()
        val testObserver = cache.setProjectAsBookmarked(projects[0].id).test()
        testObserver.assertComplete()
    }

    @Test fun setProjectAsNotBookmarkedCompletes() {
        val projects = listOf(ProjectDataFactory.makeBookmarkedProjectEntity())
        cache.saveProjects(projects).test()
        val testObserver = cache.setProjectAsNotBookmarked(projects[0].id).test()
        testObserver.assertComplete()
    }

    @Test fun areProjectsCacheReturnsData() {
        val projects = listOf(ProjectDataFactory.makeProjectEntity())
        cache.saveProjects(projects).test()
        val testObserver = cache.areProjectsCached().test()
        testObserver.assertValue(true)
    }

    @Test fun setLastCacheTimeCompletes() {
        val testObserver = cache.setLastCacheTime(1000L).test()
        testObserver.assertComplete()
    }

    @Test fun isProjectsCacheExpiredReturnsNotExpired() {
        cache.setLastCacheTime(System.currentTimeMillis()).test()
        val testObserver = cache.isProjectsCacheExpired().test()
        println(testObserver.values())
        testObserver.assertValue(false)
    }
}
