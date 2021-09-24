package com.splanes.data.feature.user.response

import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @SerializedName("results") val results: List<UserResponse>?
)
