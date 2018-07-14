package com.marcobrenes.githubtrending.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.marcobrenes.githubtrending.cache.db.ConfigConstants.QUERY_CONFIG
import com.marcobrenes.githubtrending.cache.model.Config
import io.reactivex.Flowable

@Dao
abstract class ConfigDao {

    @Query(QUERY_CONFIG)
    abstract fun getConfig(): Flowable<Config>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertConfig(config: Config)
}
