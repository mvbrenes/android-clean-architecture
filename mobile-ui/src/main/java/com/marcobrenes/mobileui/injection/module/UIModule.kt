@file:Suppress("unused")

package com.marcobrenes.mobileui.injection.module

import com.marcobrenes.githubtrending.domain.executor.BaseDispatcherProvider
import com.marcobrenes.githubtrending.domain.executor.PostExecutionThread
import com.marcobrenes.mobileui.executor.UIThread
import com.marcobrenes.mobileui.bookmarked.BookmarkedActivity
import com.marcobrenes.mobileui.browse.BrowseActivity
import com.marcobrenes.mobileui.executor.DispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UIModule {

    @Binds
    abstract fun bindPostExecutionThread(uiThread: UIThread): PostExecutionThread

    @Binds
    abstract fun bindDispatcherProvider(provider: DispatcherProvider): BaseDispatcherProvider

    @ContributesAndroidInjector
    abstract fun contributeBrowseActivity(): BrowseActivity

    @ContributesAndroidInjector
    abstract fun contributeBookmarkedActivity(): BookmarkedActivity
}
