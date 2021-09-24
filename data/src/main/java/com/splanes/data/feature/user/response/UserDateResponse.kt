package com.splanes.data.feature.user.response

import com.google.gson.annotations.SerializedName

data class UserDateResponse(
    @SerializedName("date") val date: String?,
    @SerializedName("age") val age: Int?
)
