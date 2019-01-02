package com.marcobrenes.githubtrending.testshared

import com.marcobrenes.githubtrending.domain.executor.BaseDispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.Unconfined

class TestDispatcherProvider : BaseDispatcherProvider {
    override val main: CoroutineDispatcher
        get() = Unconfined
    override val computation: CoroutineDispatcher
        get() = Unconfined
    override val io: CoroutineDispatcher
        get() = Unconfined
}