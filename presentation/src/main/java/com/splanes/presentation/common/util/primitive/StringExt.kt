package com.splanes.presentation.common.util.primitive

fun String.remove(value: String?): String =
    value?.let { replace(it, "") } ?: this