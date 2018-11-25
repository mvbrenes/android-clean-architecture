package com.marcobrenes.githubtrending.data.source

import com.marcobrenes.githubtrending.data.model.ProjectEntity
import com.marcobrenes.githubtrending.data.repository.ProjectsCache
import com.marcobrenes.githubtrending.data.test.factory.DataFactory
import com.marcobrenes.githubtrending.data.test.factory.ProjectFactory
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class ProjectsCacheDataSourceTest {

    private val cache = mock<ProjectsCache>()
    private val store = ProjectsCacheDataSource(cache)

    @Test fun getProjectsCompletes() {
        stubProjectsCacheGetProjects(Flowable.just(listOf(ProjectFactory.makeProjectEntity())))
        val testObserver = store.getProjects().test()
        testObserver.assertComplete()
    }

    @Test fun getProjectsReturnsData() {
        val data = listOf(ProjectFactory.makeProjectEntity())
        stubProjectsCacheGetProjects(Flowable.just(data))
        val testObserver = store.getProjects().test()
        testObserver.assertValue(data)
    }

    @Test fun getProjectsCallCacheSource() {
        stubProjectsCacheGetProjects(Flowable.just(listOf(ProjectFactory.makeProjectEntity())))
        store.getProjects().test()
        verify(cache).getProjects()
    }

    @Test fun saveProjectsCompletes() {
        stubProjectsCacheSaveProjects(Completable.complete())
        stubProjectsCacheSetLastCacheTime(Completable.complete())
        val testObserver = store.saveProjects(listOf(ProjectFactory.makeProjectEntity())).test()
        testObserver.assertComplete()
    }

    @Test fun saveProjectsCallCache() {
        val data = listOf(ProjectFactory.makeProjectEntity())
        stubProjectsCacheSaveProjects(Completable.complete())
        stubProjectsCacheSetLastCacheTime(Completable.complete())
        store.saveProjects(data).test()
        verify(cache).saveProjects(data)
    }

    @Test fun saveProjectsCallCacheStore() {
        val data = listOf(ProjectFactory.makeProjectEntity())
        stubProjectsCacheSaveProjects(Completable.complete())
        stubProjectsCacheSetLastCacheTime(Completable.complete())
        store.saveProjects(data).test()
        verify(cache).saveProjects(data)
    }

    @Test fun clearProjectsCompletes() {
        stubProjectsClearProjects(Completable.complete())
        val testObserver = store.clearProjects().test()
        testObserver.assertComplete()
    }

    @Test fun clearProjectsCallsCacheStore() {
        stubProjectsClearProjects(Completable.complete())
        store.clearProjects().test()
        verify(cache).clearProjects()
    }

    @Test fun getBookmarkedProjectsCompletes() {
        stubProjectsCacheGetBookmarkedProjects(Observable.just(listOf(
                ProjectFactory.makeProjectEntity())))
        val testObserver = store.getBookmarkedProjects().test()
        testObserver.assertComplete()
    }

    @Test fun getBookmarkedProjectsCallsCacheStore() {
        stubProjectsCacheGetBookmarkedProjects(Observable.just(listOf(
                ProjectFactory.makeProjectEntity())))
        store.getBookmarkedProjects().test()
        verify(cache).getBookmarkedProjects()
    }

    @Test fun getBookmarkedProjectsReturnsData() {
        val data = listOf(ProjectFactory.makeProjectEntity())
        stubProjectsCacheGetBookmarkedProjects(Observable.just(data))
        val testObserver = store.getBookmarkedProjects().test()
        testObserver.assertValue(data)
    }

    @Test fun setProjectAsBookmarkedCompletes() {
        stubProjectsCacheSetProjectsAsBookmarked(Completable.complete())
        val testObserver = store.setProjectAsBookmarked(DataFactory.randomString()).test()
        testObserver.assertComplete()
    }

    @Test fun setProjectAsBookmarkedCallsCacheStore() {
        val projectId = DataFactory.randomString()
        stubProjectsCacheSetProjectsAsBookmarked(Completable.complete())
        store.setProjectAsBookmarked(projectId).test()
        verify(cache).setProjectAsBookmarked(projectId)
    }

    @Test fun setProjectAsNotBookmarkedCompletes() {
        stubProjectsCacheSetProjectsAsNotBookmarked(Completable.complete())
        val testObserver = store.setProjectAsNotBookmarked(DataFactory.randomString()).test()
        testObserver.assertComplete()
    }

    @Test fun setProjectAsNotBookmarkedCallsCacheStore() {
        val projectId = DataFactory.randomString()
        stubProjectsCacheSetProjectsAsNotBookmarked(Completable.complete())
        store.setProjectAsNotBookmarked(projectId).test()
        verify(cache).setProjectAsNotBookmarked(projectId)
    }

    private fun stubProjectsCacheGetProjects(stub: Flowable<List<ProjectEntity>>) {
        whenever(cache.getProjects()) doReturn stub
    }

    private fun stubProjectsCacheSaveProjects(completable: Completable) {
        whenever(cache.saveProjects(any())) doReturn completable
    }

    private fun stubProjectsCacheSetLastCacheTime(completable: Completable) {
        whenever(cache.setLastCacheTime(any())) doReturn completable
    }

    private fun stubProjectsClearProjects(completable: Completable) {
        whenever(cache.clearProjects()) doReturn completable
    }

    private fun stubProjectsCacheGetBookmarkedProjects(observable: Observable<List<ProjectEntity>>) {
        whenever(cache.getBookmarkedProjects()) doReturn observable
    }

    private fun stubProjectsCacheSetProjectsAsBookmarked(completable: Completable) {
        whenever(cache.setProjectAsBookmarked(any())) doReturn completable
    }

    private fun stubProjectsCacheSetProjectsAsNotBookmarked(completable: Completable) {
        whenever(cache.setProjectAsNotBookmarked(any())) doReturn completable
    }
}
