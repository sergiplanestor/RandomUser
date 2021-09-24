package com.splanes.data.feature.user.response

import com.google.gson.annotations.SerializedName

data class UserStreetResponse(
    @SerializedName("number") val num: Int?,
    @SerializedName("name") val name: String?
) {
    fun build(): String? = name?.plus(" ${num ?: ""}")
}
