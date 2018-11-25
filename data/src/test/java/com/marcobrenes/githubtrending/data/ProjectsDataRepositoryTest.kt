package com.marcobrenes.githubtrending.data

import com.marcobrenes.githubtrending.data.mapper.ProjectMapper
import com.marcobrenes.githubtrending.data.model.ProjectEntity
import com.marcobrenes.githubtrending.data.repository.ProjectsCache
import com.marcobrenes.githubtrending.data.repository.ProjectsDataSource
import com.marcobrenes.githubtrending.data.source.ProjectsDataSourceFactory
import com.marcobrenes.githubtrending.data.test.factory.DataFactory
import com.marcobrenes.githubtrending.data.test.factory.ProjectFactory
import com.marcobrenes.githubtrending.domain.model.Project
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ProjectsDataRepositoryTest {

    private val mapper = mock<ProjectMapper>()
    private val factory = mock<ProjectsDataSourceFactory>()
    private val store = mock<ProjectsDataSource>()
    private val cache = mock<ProjectsCache>()
    private val repository = ProjectsDataRepository(mapper, cache, factory)

    @Before fun setup() {
        stubFactoryGetDataSource()
        stubFactoryGetCacheDataSource()
        stubIsCacheExpired(Single.just(false))
        stubAreProjectsCached(Single.just(false))
        stubSaveProjects(Completable.complete())
    }

    @Test fun getProjectsCompletes() {
        stubGetProjects(Flowable.just(listOf(ProjectFactory.makeProjectEntity())))
        stubMapper(ProjectFactory.makeProject(), any())

        val testObserver = repository.getProjects().test()
        testObserver.assertComplete()
    }

    @Test fun getProjectsReturnsData() {
        val projectEntity = ProjectFactory.makeProjectEntity()
        val project = ProjectFactory.makeProject()
        stubGetProjects(Flowable.just(listOf(projectEntity)))
        stubMapper(project, projectEntity)

        val testObserver = repository.getProjects().test()
        testObserver.assertValue(listOf(project))
    }

    @Test fun getBookmarkedProjectsCompletes() {
        stubGetBookmarkedProjects(Observable.just(listOf(ProjectFactory.makeProjectEntity())))
        stubMapper(ProjectFactory.makeProject(), any())

        val testObserver = repository.getBookmarkedProjects().test()
        testObserver.assertComplete()
    }

    @Test fun getBookmarkedProjectsReturnsData() {
        val projectEntity = ProjectFactory.makeProjectEntity()
        val project = ProjectFactory.makeProject()
        stubGetBookmarkedProjects(Observable.just(listOf(projectEntity)))
        stubMapper(project, projectEntity)

        val testObserver = repository.getBookmarkedProjects().test()
        testObserver.assertValue(listOf(project))
    }

    @Test fun bookmarkProjectCompletes() {
        stubBookmarkProject(Completable.complete())

        val testObserver = repository.bookmarkProject(DataFactory.randomString()).test()
        testObserver.assertComplete()
    }

    @Test fun unbookmarkProjectCompletes() {
        stubUnbookmarkProject(Completable.complete())

        val testObserver = repository.unbookmarkProject(DataFactory.randomString()).test()
        testObserver.assertComplete()
    }

    private fun stubBookmarkProject(completable: Completable) {
        whenever(store.setProjectAsBookmarked(any())) doReturn completable
    }

    private fun stubUnbookmarkProject(completable: Completable) {
        whenever(store.setProjectAsNotBookmarked(any())) doReturn completable
    }

    private fun stubIsCacheExpired(single: Single<Boolean>) {
        whenever(cache.isProjectsCacheExpired()) doReturn single
    }

    private fun stubAreProjectsCached(single: Single<Boolean>) {
        whenever(cache.areProjectsCached()) doReturn single
    }

    private fun stubMapper(model: Project, entity: ProjectEntity) {
        whenever(mapper.mapFromEntity(entity)) doReturn model
    }

    private fun stubGetProjects(stub: Flowable<List<ProjectEntity>>) {
        whenever(store.getProjects()) doReturn stub
    }

    private fun stubGetBookmarkedProjects(observable: Observable<List<ProjectEntity>>) {
        whenever(store.getBookmarkedProjects()) doReturn observable
    }

    private fun stubFactoryGetDataSource() {
        whenever(factory.getDataSource(any(), any())) doReturn store
    }

    private fun stubFactoryGetCacheDataSource() {
        whenever(factory.getCacheDataSource()) doReturn store
    }

    private fun stubSaveProjects(completable: Completable) {
        whenever(store.saveProjects(any())) doReturn completable
    }
}
