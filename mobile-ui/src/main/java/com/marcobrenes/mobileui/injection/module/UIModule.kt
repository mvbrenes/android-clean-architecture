package com.marcobrenes.mobileui.injection.module

import com.marcobrenes.githubtrending.domain.executor.PostExecutionThread
import com.marcobrenes.mobileui.browse.BrowseActivity
import com.marcobrenes.mobileui.UIThread
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UIModule {

    @Binds
    abstract fun bindPostExecutionThread(uiThread: UIThread): PostExecutionThread

    @ContributesAndroidInjector
    abstract fun contributeBrowseActivity(): BrowseActivity

}
