package com.splanes.data.feature.user.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("gender") val gender: String?,
    @SerializedName("name") val name: UserNameResponse?,
    @SerializedName("location") val location: UserLocationResponse?,
    @SerializedName("email") val email: String?,
    @SerializedName("login") val login: UserLoginResponse?,
    @SerializedName("dob") val birthday: String?,
    @SerializedName("registered") val registeredOn: String?,
    @SerializedName("phone") val phone: String?,
    @SerializedName("cell") val cell: String?,
    @SerializedName("id") val id: UserIdResponse?,
    @SerializedName("picture") val picture: UserPictureResponse?,
    @SerializedName("nat") val nationality: String?,
)
