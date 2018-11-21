package com.marcobrenes.mobileui.bookmarked

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.marcobrenes.githubtrending.domain.model.Project
import com.marcobrenes.mobileui.R
import com.marcobrenes.mobileui.TestApplication
import com.marcobrenes.mobileui.ext.ActivityTestRule
import com.marcobrenes.mobileui.test.factory.ProjectFactory
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BookmarkedActivityUITest {

    @get:Rule val activity = ActivityTestRule<BookmarkedActivity>(launchActivity = false)

    @Test fun activityLaunches() {
        stubProjectsRepositoryGetBookmarkedProjects(Observable.just(
                ProjectFactory.makeProjectList(1)))
        activity.launchActivity(null)
    }

    @Test fun bookmarkedProjectsDisplay() {
        val projects = ProjectFactory.makeProjectList(10)
        stubProjectsRepositoryGetBookmarkedProjects(Observable.just(projects))
        activity.launchActivity(null)
        projects.forEachIndexed { index, project ->
            onView(withId(R.id.recycler_view))
                    .perform(RecyclerViewActions
                            .scrollToPosition<BookmarkedAdapter.ViewHolder>(index))
            onView(withId(R.id.recycler_view))
                    .check(matches(hasDescendant(withText(project.fullName))))
        }
    }

    private fun stubProjectsRepositoryGetBookmarkedProjects(observable: Observable<List<Project>>) {
        val repoMock = TestApplication.appComponent().projectsRepository()
        whenever(repoMock.getBookmarkedProjects()) doReturn observable
    }

}