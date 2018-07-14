package com.marcobrenes.cache.test.factory

import com.marcobrenes.cache.model.Config

object ConfigDataFactory {
    fun makeCachedConfig(): Config {
        return Config(DataFactory.randomInt(), DataFactory.randomLong())
    }
}
