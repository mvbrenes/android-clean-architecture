package com.marcobrenes.mobileui.injection

import com.marcobrenes.githubtrending.domain.repository.ProjectsRepository
import com.nhaarman.mockitokotlin2.mock
import dagger.Module
import dagger.Provides

@Module
object TestDataModule {
    @Provides
    @JvmStatic
    fun provideDataRepository(): ProjectsRepository = mock()
}
