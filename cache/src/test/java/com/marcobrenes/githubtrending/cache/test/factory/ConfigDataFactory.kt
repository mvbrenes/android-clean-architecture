package com.marcobrenes.githubtrending.cache.test.factory

import com.marcobrenes.githubtrending.cache.model.Config

object ConfigDataFactory {
    fun makeCachedConfig(): Config {
        return Config(DataFactory.randomInt(), DataFactory.randomLong())
    }
}
