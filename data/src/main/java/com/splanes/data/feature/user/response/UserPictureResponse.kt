package com.splanes.data.feature.user.response

import com.google.gson.annotations.SerializedName

data class UserPictureResponse(
    @SerializedName("large") val large: String?,
    @SerializedName("medium") val medium: String?,
    @SerializedName("thumbnail") val thumbnail: String?
)
