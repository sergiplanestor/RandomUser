package com.splanes.data.feature.user.response

import com.google.gson.annotations.SerializedName

data class UserNameResponse(
    @SerializedName("title") val title: String?,
    @SerializedName("first") val first: String?,
    @SerializedName("last") val last: String?
)
