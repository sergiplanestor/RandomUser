package com.splanes.domain.common.net

import com.splanes.domain.R
import com.splanes.domain.common.throwable.CustomThrowable

object ThrowableHandler {

    fun findOrDefault(throwable: Throwable?): Int =
        when (throwable as? CustomThrowable) {
            CustomThrowable.NoInternetConnection -> R.string.error_no_internet_message
            CustomThrowable.Timeout -> R.string.error_timeout_message
            // CustomThrowable.Unknown
            else -> R.string.error_default_message
        }

}