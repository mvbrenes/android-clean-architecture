package com.marcobrenes.mobileui.bookmarked

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marcobrenes.githubtrending.presentation.BrowseBookmarkedProjectsViewModel
import com.marcobrenes.githubtrending.presentation.model.ProjectView
import com.marcobrenes.githubtrending.presentation.state.Resource
import com.marcobrenes.githubtrending.presentation.state.ResourceState
import com.marcobrenes.mobileui.R
import com.marcobrenes.mobileui.ext.get
import com.marcobrenes.mobileui.ext.intentOf
import com.marcobrenes.mobileui.injection.ViewModelFactory
import com.marcobrenes.mobileui.mapper.ProjectViewMapper
import com.marcobrenes.mobileui.model.Project
import dagger.android.AndroidInjection
import javax.inject.Inject


class BookmarkedActivity : AppCompatActivity() {

    @Inject lateinit var adapter: BookmarkedAdapter
    @Inject lateinit var mapper: ProjectViewMapper
    @Inject lateinit var viewModelFactory: ViewModelFactory
    
    private lateinit var viewModel: BrowseBookmarkedProjectsViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var progress: ProgressBar

    companion object {
        fun getStartIntent(context: Context): Intent {
            return intentOf<BookmarkedActivity>(context)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmarked)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get()
        recyclerView = findViewById(R.id.recycler_view)
        progress = findViewById(R.id.progress)

        setupBrowseRecycler()
        viewModel.getProjects().observe(this, dataStateObserver)
        viewModel.fetchProjects()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupBrowseRecycler() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private val dataStateObserver = Observer<Resource<List<ProjectView>>> { resource ->
        when (resource.status) {
            ResourceState.SUCCESS -> {
                setupScreenForSuccess(resource.data?.map { mapper.mapToView(it) })
                progress.isVisible = false
                recyclerView.isVisible = true
            }

            ResourceState.LOADING -> {
                progress.isVisible = true
                recyclerView.isVisible = false
            }

            ResourceState.ERROR -> {
            }
        }
    }

    private fun setupScreenForSuccess(projects: List<Project>?) {
        projects?.let {
            adapter.projects = it
            adapter.notifyDataSetChanged()
        }
    }
}
