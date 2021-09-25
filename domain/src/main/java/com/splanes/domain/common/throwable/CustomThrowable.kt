package com.splanes.domain.common.throwable

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class CustomThrowable(override val cause: Throwable? = null) : Throwable(cause), Parcelable {

    @Parcelize
    object NoInternetConnection : CustomThrowable()

    @Parcelize
    object Timeout : CustomThrowable()

    @Parcelize
    object Unknown : CustomThrowable()

}
