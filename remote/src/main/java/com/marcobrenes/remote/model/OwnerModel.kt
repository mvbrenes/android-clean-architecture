package com.marcobrenes.remote.model

import com.google.gson.annotations.SerializedName

class OwnerModel(@SerializedName("login") val ownerName: String,
                 @SerializedName("avatar_url") val ownerAvatar: String)
