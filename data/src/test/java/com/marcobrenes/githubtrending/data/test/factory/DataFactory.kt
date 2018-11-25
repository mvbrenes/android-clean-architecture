package com.marcobrenes.githubtrending.data.test.factory

import java.util.*

internal object DataFactory {

    fun randomString(): String {
        return UUID.randomUUID().toString()
    }

    fun randomBoolean(): Boolean {
        return Math.random() < 0.5
    }
}
