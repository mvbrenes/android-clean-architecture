package com.marcobrenes.githubtrending.domain.interactor

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel

abstract class CoroutineChannelUseCase<T, in Params>(
    private val subscribeDispatcher: CoroutineDispatcher,
    private val observeDispatcher: CoroutineDispatcher
) {

    private val parentJob = Job()
    private val scope = CoroutineScope(observeDispatcher + parentJob)

    abstract suspend fun getChannel(params: Params? = null): ReceiveChannel<T>

    operator fun invoke(params: Params? = null, callback: CoroutineCallback<T>) {
        launchLoad(params, callback)
    }

    private fun launchLoad(params: Params?, callback: CoroutineCallback<T>) =
        scope.launch(subscribeDispatcher) {
            try {
                val channel = getChannel(params)
                for (item in channel) {
                    withContext(observeDispatcher) {
                        callback.sourceLoaded(item)
                    }
                }
            } catch (e: Exception) {
                withContext(observeDispatcher) {
                    callback.loadFailed(e)
                }
            }
        }

    fun dispose() {
        parentJob.cancel()
    }
}