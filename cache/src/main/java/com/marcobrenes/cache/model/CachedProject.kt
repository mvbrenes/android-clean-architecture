package com.marcobrenes.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.marcobrenes.cache.db.ProjectConstants.COLUMN_PROJECT_ID
import com.marcobrenes.cache.db.ProjectConstants.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class CachedProject(
        @PrimaryKey
        @ColumnInfo(name = COLUMN_PROJECT_ID)
        var id: String,
        var name: String,
        var fullName: String,
        var starCount: String,
        var dateCreated: String,
        var ownerName: String,
        var ownerAvatar: String,
        var isBookmarked: Boolean)
