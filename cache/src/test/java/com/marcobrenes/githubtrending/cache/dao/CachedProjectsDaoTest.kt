package com.marcobrenes.githubtrending.cache.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import com.marcobrenes.githubtrending.cache.db.ProjectsDatabase
import com.marcobrenes.githubtrending.cache.test.factory.ProjectDataFactory
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class CachedProjectsDaoTest {

    @Rule
    @JvmField var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val database = Room.inMemoryDatabaseBuilder(
            RuntimeEnvironment.application.applicationContext,
            ProjectsDatabase::class.java)
            .allowMainThreadQueries()
            .build()

    @After fun closeDb() {
        database.close()
    }

    @Test fun countSavedProjectsReturnsData() {
        val projects = with(ProjectDataFactory) { listOf(makeCachedProject(), makeCachedProject()) }
        database.cachedProjectsDao().insertProjects(projects)
        val testObserver = database.cachedProjectsDao().countSavedProjects().test()
        testObserver.assertValue(projects.size)
    }

    @Test fun getProjectsReturnsData() {
        val project = ProjectDataFactory.makeCachedProject()
        database.cachedProjectsDao().insertProjects(listOf(project))
        val testObserver = database.cachedProjectsDao().getProjects().test()
        testObserver.assertValue(listOf(project))
    }

    @Test fun deleteProjectsClearsData() {
        val project = ProjectDataFactory.makeCachedProject()
        database.cachedProjectsDao().insertProjects(listOf(project))
        database.cachedProjectsDao().deleteProjects()
        val testObserver = database.cachedProjectsDao().getProjects().test()
        testObserver.assertValue(emptyList())
    }

    @Test fun getBookmarkedProjectsReturnsData() {
        val project = ProjectDataFactory.makeCachedProject()
        val bookmarkedProject = ProjectDataFactory.makeCachedBookmarkedProject()
        database.cachedProjectsDao().insertProjects(listOf(project, bookmarkedProject))
        val testObserver = database.cachedProjectsDao().getBookmarkedProjects().test()
        testObserver.assertValue(listOf(bookmarkedProject))
    }

    @Test fun setProjectAsBookmarkedSavesData() {
        val project = ProjectDataFactory.makeCachedProject()
        database.cachedProjectsDao().insertProjects(listOf(project))
        database.cachedProjectsDao().setBookmarkStatus(true, project.id)
        project.isBookmarked = true
        val testObserver = database.cachedProjectsDao().getBookmarkedProjects().test()
        testObserver.assertValue(listOf(project))
    }

    @Test fun setProjectAsNotBookmarkedSavesData() {
        val project = ProjectDataFactory.makeCachedBookmarkedProject()
        database.cachedProjectsDao().insertProjects(listOf(project))
        database.cachedProjectsDao().setBookmarkStatus(false, project.id)
        val testObserver = database.cachedProjectsDao().getBookmarkedProjects().test()
        testObserver.assertValue(emptyList())
    }
}
