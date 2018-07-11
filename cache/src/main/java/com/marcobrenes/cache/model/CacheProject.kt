package com.marcobrenes.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.marcobrenes.cache.db.ProjectConstants

@Entity(tableName = ProjectConstants.TABLE_NAME)
data class CacheProject(
        @PrimaryKey var id: String,
        var name: String,
        var fullName: String,
        var starCount: String,
        var dateCreated: String,
        var ownerName: String,
        var ownerAvatar: String,
        var isBookmarked: Boolean)
