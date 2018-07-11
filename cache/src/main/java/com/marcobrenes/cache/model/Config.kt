package com.marcobrenes.cache.model

import androidx.room.Entity
import com.marcobrenes.cache.db.ConfigConstants.TABLE_NAME

@Entity(tableName = TABLE_NAME)
class Config(val lastCacheTime: Long)