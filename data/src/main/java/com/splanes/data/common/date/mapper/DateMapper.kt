package com.splanes.data.common.date.mapper

import com.splanes.domain.common.date.model.DateModel
import java.text.SimpleDateFormat
import java.util.*

object DateMapper {

    private const val RESPONSE_FORMAT = "YYYY-MM-DD'T'HH:mm:ss.SSS'Z'"
    private const val MODEL_FORMAT = "dd/MM/YY"

    fun mapToDateModel(response: String?): DateModel? =
        response?.let {
            sdfOf(RESPONSE_FORMAT).parse(it)?.run { mapToDateModel(time) }
        }

    fun mapToDateModel(millis: Long?): DateModel? =
        millis?.let {
            DateModel(
                millis = it,
                formatted = sdfOf(MODEL_FORMAT).format(it)
            )
        }

    private fun sdfOf(pattern: String): SimpleDateFormat =
        SimpleDateFormat(pattern, Locale.getDefault())
}