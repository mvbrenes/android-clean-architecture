package com.marcobrenes.githubtrending.domain.executor

import kotlinx.coroutines.CoroutineDispatcher

interface BaseDispatcherProvider {
    val main: CoroutineDispatcher
    val computation: CoroutineDispatcher
    val io: CoroutineDispatcher
}