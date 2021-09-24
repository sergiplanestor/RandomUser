package com.splanes.data.common.date.util

import com.splanes.domain.common.date.model.DateModel

fun DateModel?.orEmpty(): DateModel =
    DateModel(
        millis = 0L,
        formatted = ""
    )