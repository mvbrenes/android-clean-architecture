package com.marcobrenes.githubtrending.domain.interactor

import com.marcobrenes.githubtrending.domain.executor.PostExecutionThread
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

abstract class ObservableUseCase<T, in Params> constructor(
        private val postExecutionThread: PostExecutionThread) {

    private val disposables = CompositeDisposable()

    abstract fun buildUseCaseObservable(params: Params? = null): Observable<T>

    open fun execute(observer: DisposableObserver<T>, params: Params? = null) {
        val observable = this.buildUseCaseObservable(params)
                .subscribeOn(Schedulers.io())
                .observeOn(postExecutionThread.scheduler)
        addDisposable(observable.subscribeWith(observer))
    }

    private fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    fun dispose() {
        if (!disposables.isDisposed) disposables.dispose()
    }
}
