package com.marcobrenes.mobileui.injection.module

import com.marcobrenes.githubtrending.data.ProjectsDataRepository
import com.marcobrenes.githubtrending.domain.repository.ProjectsRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {

    @Binds
    abstract fun bindDataRepository(dataRepository: ProjectsDataRepository): ProjectsRepository
}
