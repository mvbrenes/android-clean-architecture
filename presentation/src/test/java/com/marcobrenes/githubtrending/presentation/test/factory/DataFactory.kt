package com.marcobrenes.githubtrending.presentation.test.factory

import java.util.*

object DataFactory {
    fun randomString(): String {
        return UUID.randomUUID().toString()
    }

    fun randomBoolean(): Boolean {
        return Math.random() < 0.5
    }
}