package com.marcobrenes.mobileui

import android.app.Application
import timber.log.Timber

class GithubTrendingApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupTimber()
    }

    private fun setupTimber() {
        Timber.plant(Timber.DebugTree())
    }
}
