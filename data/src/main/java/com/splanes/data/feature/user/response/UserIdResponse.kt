package com.splanes.data.feature.user.response

import com.google.gson.annotations.SerializedName

data class UserIdResponse(
    @SerializedName("name") val name: String?,
    @SerializedName("value") val value: String?
)
