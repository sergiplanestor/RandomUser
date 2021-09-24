package com.splanes.data.common.date.mapper

import com.splanes.domain.common.date.model.DateModel
import java.text.SimpleDateFormat
import java.util.*

object DateMapper {

    private const val RESPONSE_FORMAT = "YYYY-MM-DD HH:mm:ss"
    private const val MODEL_FORMAT = "dd/MM/YY"

    fun mapToDateModel(response: String?): DateModel? =
        response?.let {
            sdfOf(RESPONSE_FORMAT).parse(it)?.run {
                DateModel(
                    millis = time,
                    formatted = sdfOf(MODEL_FORMAT).format(time)
                )
            }
        }

    private fun sdfOf(pattern: String): SimpleDateFormat =
        SimpleDateFormat(pattern, Locale.getDefault())
}