package com.marcobrenes.githubtrending.presentation.test

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

class LiveDataTestObserver<T> : Observer<T> {

    val values = mutableListOf<T?>()

    override fun onChanged(value: T?) {
        values.add(value)
    }
}

internal fun <T> LiveData<T>.testObserver() = LiveDataTestObserver<T>().also { observeForever(it) }