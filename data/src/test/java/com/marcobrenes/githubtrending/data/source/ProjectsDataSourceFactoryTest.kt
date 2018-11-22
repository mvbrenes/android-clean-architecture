package com.marcobrenes.githubtrending.data.source

import com.nhaarman.mockitokotlin2.mock
import org.junit.Test
import kotlin.test.assertEquals

class ProjectsDataSourceFactoryTest {

    private val cacheStore = mock<ProjectsCacheDataSource>()
    private val remoteStore = mock<ProjectsRemoteDataSource>()
    private val factory = ProjectsDataSourceFactory(cacheStore, remoteStore)

    @Test fun getDataSourceReturnsRemoteStoreWhenCacheExpired() {
        assertEquals(remoteStore, factory.getDataSource(true, true))
    }

    @Test fun getDataSourceReturnsRemoteStoreWhenProjectsNotCached() {
        assertEquals(remoteStore, factory.getDataSource(false, false))
    }

    @Test fun getDataSourceReturnsCacheStore() {
        assertEquals(cacheStore, factory.getDataSource(true, false))
    }

    @Test fun getCacheDataSourceReturnsCacheStore() {
        assertEquals(cacheStore, factory.getCacheDataSource())
    }
}
