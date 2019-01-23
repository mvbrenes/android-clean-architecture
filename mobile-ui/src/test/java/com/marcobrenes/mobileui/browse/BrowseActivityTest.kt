package com.marcobrenes.mobileui.browse

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.marcobrenes.githubtrending.domain.model.Project
import com.marcobrenes.mobileui.R
import com.marcobrenes.mobileui.TestApplication
import com.marcobrenes.mobileui.ext.ActivityTestRule
import com.marcobrenes.mobileui.test.RecyclerViewMatcher
import com.marcobrenes.mobileui.test.factory.ProjectFactory
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(application = TestApplication::class, sdk = [28])
class BrowseActivityTest {

    @get:Rule val activity = ActivityTestRule<BrowseActivity>(launchActivity = false)

    @Test fun activityLaunches() = runBlocking<Unit> {
        stubProjectsRepositoryGetProjects(ProjectFactory.makeProjectList(1))
        activity.launchActivity(null)
        onView(withId(R.id.recycler_view))
            .check(matches(ViewMatchers.isDisplayed()))
    }

    @Test fun projectsDisplay() = runBlocking {
        val projects = ProjectFactory.makeProjectList(10)
        stubProjectsRepositoryGetProjects(projects)
        activity.launchActivity(null)

        projects.forEachIndexed { index, project ->
            onView(withId(R.id.recycler_view))
                .perform(
                    RecyclerViewActions
                        .scrollToPosition<BrowseAdapter.ViewHolder>(index)
                )

            RecyclerViewMatcher(R.id.recycler_view)
                .atPositionOnView(index, R.id.text_project_name)
                .matches(withText(project.fullName))
        }
    }

    private suspend fun stubProjectsRepositoryGetProjects(projects: List<Project>) {
        val repoMock = TestApplication.appComponent().projectsRepository()
        whenever(repoMock.getProjects()) doReturn projects.asReceiveChannel()
    }

    private fun <T> T.asReceiveChannel(context: CoroutineContext = Dispatchers.Unconfined) =
        GlobalScope.produce(context) { send(this@asReceiveChannel) }
}
