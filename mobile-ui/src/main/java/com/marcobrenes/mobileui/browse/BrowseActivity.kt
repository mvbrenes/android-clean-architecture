package com.marcobrenes.mobileui.browse

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.marcobrenes.githubtrending.presentation.BrowseProjectsViewModel
import com.marcobrenes.githubtrending.presentation.model.ProjectView
import com.marcobrenes.githubtrending.presentation.state.Resource
import com.marcobrenes.githubtrending.presentation.state.ResourceState
import com.marcobrenes.mobileui.R
import com.marcobrenes.mobileui.bookmarked.BookmarkedActivity
import com.marcobrenes.mobileui.ext.get
import com.marcobrenes.mobileui.injection.ViewModelFactory
import com.marcobrenes.mobileui.mapper.ProjectViewMapper
import com.marcobrenes.mobileui.model.Project
import dagger.android.AndroidInjection
import timber.log.Timber
import javax.inject.Inject

class BrowseActivity : AppCompatActivity() {

    @Inject lateinit var mapper: ProjectViewMapper
    @Inject lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: BrowseProjectsViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var progress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get()
        recyclerView = findViewById(R.id.recycler_view)
        progress = findViewById(R.id.progress)

        setupBrowseRecycler()
        viewModel.getProjects().observe(this, dataStateObserver)
        viewModel.fetchProjects()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_bookmarked -> {
                startActivity(BookmarkedActivity.getStartIntent(this))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupScreenForSuccess(projects: List<Project>?) {
        if (progress.isVisible) {
            progress.isVisible = false
        }
        projects?.let { list -> (recyclerView.adapter as? BrowseAdapter)?.submitList(list) }
    }

    private fun setupBrowseRecycler() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = BrowseAdapter().apply { projectListener = listener }
        }
    }

    private val dataStateObserver = Observer<Resource<List<ProjectView>>> { resource ->
        when (resource.status) {
            ResourceState.SUCCESS -> {
                setupScreenForSuccess(resource.data?.map { mapper.mapToView(it) })
            }
            ResourceState.LOADING -> {
                if (recyclerView.adapter?.itemCount ?: 0 == 0 ) {
                    progress.isVisible = true
                }
            }
            ResourceState.ERROR -> {
                resource.message?.let { Timber.e(it) }
                progress.isVisible = false
                findViewById<View?>(android.R.id.content)?.let {
                    Snackbar.make(it, resource.message ?: "", Snackbar.LENGTH_INDEFINITE).show()
                }
            }
        }
    }

    private val listener = object : ProjectListener {
        override fun onBookmarkedProjectClicked(projectId: String) {
            viewModel.unBookmarkProject(projectId)
        }

        override fun onProjectClicked(projectId: String) {
            viewModel.bookmarkProject(projectId)
        }
    }
}
