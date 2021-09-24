package com.splanes.domain.common.net

import com.splanes.domain.R

object ThrowableHandler {

    fun findOrDefault(throwable: Throwable?): Int =
        when (throwable) {
            else -> R.string.error_default_message
        }

}