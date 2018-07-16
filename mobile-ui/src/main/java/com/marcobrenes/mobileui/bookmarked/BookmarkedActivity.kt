package com.marcobrenes.mobileui.bookmarked

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
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
import kotlinx.android.synthetic.main.activity_bookmarked.*
import javax.inject.Inject


class BookmarkedActivity : AppCompatActivity() {

    @Inject lateinit var adapter: BookmarkedAdapter
    @Inject lateinit var mapper: ProjectViewMapper
    @Inject lateinit var viewModelFactory: ViewModelFactory
    @Inject lateinit var browseViewModel: BrowseBookmarkedProjectsViewModel

    companion object {
        fun getStartIntent(context: Context): Intent {
            return intentOf<BookmarkedActivity>(context)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmarked)
        AndroidInjection.inject(this)
        browseViewModel = ViewModelProviders.of(this, viewModelFactory).get()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            } else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onStart() {
        super.onStart()
        browseViewModel.getProjects().observe(this, Observer {
            handleDataState(it)
        })
        browseViewModel.fetchProjects()
    }

    private fun setupBrowseRecyler() {
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapter
    }

    private fun handleDataState(resource: Resource<List<ProjectView>>) {
        when(resource.status) {
            ResourceState.SUCCESS -> {
                setupScreenForSuccess(resource.data?.map { mapper.mapToView(it) })
                progress.isVisible = false
                recycler_view.isVisible = true
            }

            ResourceState.LOADING -> {
                progress.isVisible = true
                recycler_view.isVisible = false
            }

            ResourceState.ERROR -> {}
        }
    }

    private fun setupScreenForSuccess(projects: List<Project>?) {
        projects?.let {
            adapter.projects = it
            adapter.notifyDataSetChanged()
        } ?: run {

        }
    }
}