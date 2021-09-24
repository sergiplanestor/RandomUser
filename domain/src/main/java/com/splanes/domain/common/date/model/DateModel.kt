package com.splanes.domain.common.date.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DateModel(
    val millis: Long,
    val formatted: String
): Parcelable
