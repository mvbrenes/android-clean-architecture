package com.marcobrenes.githubtrending.domain.interactor

import java.lang.Exception

interface CoroutineCallback<T> {

    fun sourceLoaded(result: T)

    fun loadFailed(exception: Exception)
}