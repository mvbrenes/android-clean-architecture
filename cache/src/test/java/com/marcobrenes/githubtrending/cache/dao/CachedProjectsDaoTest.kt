package com.marcobrenes.githubtrending.cache.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.marcobrenes.githubtrending.cache.db.ProjectsDatabase
import com.marcobrenes.githubtrending.cache.test.factory.ProjectDataFactory
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class CachedProjectsDaoTest {

    @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ProjectsDatabase::class.java)
            .allowMainThreadQueries()
            .build()

    @After fun closeDb() {
        database.close()
    }

    @Test fun cachedProjectsExistReturnsData() {
        val projects = with(ProjectDataFactory) { listOf(makeCachedProject(), makeCachedProject()) }
        database.cachedProjectsDao().insertProjects(projects)
        val testObserver = database.cachedProjectsDao().cachedProjectsExist().test()
        testObserver.assertValue(1)
    }

    @Test fun `cachedProjects returns zero when cache empty`() {
        val testObserver = database.cachedProjectsDao().cachedProjectsExist().test()
        testObserver.assertValue(0)
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
