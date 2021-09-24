package com.splanes.domain.feature.user.model

import android.os.Parcelable
import com.splanes.domain.common.date.model.DateModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val gender: UserGender,
    val title: String,
    val name: String,
    val surname: String,
    val address: String,
    val email: String,
    val birthday: DateModel,
    val registeredOn: DateModel,
    val phone: String,
    val mobile: String,
    val thumbnailUrl: String,
    val imageLargeUrl: String,
    val imageUrl: String,
    val nationality: String
): Parcelable
