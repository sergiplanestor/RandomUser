package com.splanes.presentation.common.util.view

import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.view.isVisible

fun TextView.setTextOrGone(@StringRes resource: Int?): Boolean =
    (resource?.let {
        setText(it)
        true
    } ?: false).also { isVisible = resource != null }

inline var TextView.textOrGone: CharSequence?
    get() = text
    set(value) {
        text = value
        isVisible = value.isNullOrBlank().not()
    }