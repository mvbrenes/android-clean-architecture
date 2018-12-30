package com.marcobrenes.mobileui.executor

import com.marcobrenes.githubtrending.domain.executor.BaseDispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DispatcherProvider : BaseDispatcherProvider {
    override val main: CoroutineDispatcher
        get() = Dispatchers.Main
    override val computation: CoroutineDispatcher
        get() = Dispatchers.Default
    override val io: CoroutineDispatcher
        get() = Dispatchers.IO
}