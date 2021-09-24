package com.splanes.data.feature.user.response

import com.google.gson.annotations.SerializedName

data class UserLocationResponse(
    @SerializedName("street") val street: String?,
    @SerializedName("city") val city: String?,
    @SerializedName("state") val state: String?,
    @SerializedName("postcode") val zipCode: String?
)
