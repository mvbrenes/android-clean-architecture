package com.marcobrenes.mobileui.injection

import com.marcobrenes.githubtrending.domain.repository.ProjectsRepository
import com.nhaarman.mockitokotlin2.mock
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object TestDataModule {

    @Provides @Singleton @JvmStatic
    fun provideDataRepository(): ProjectsRepository = mock()
}
