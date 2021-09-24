package com.splanes.presentation.common.util

import android.widget.TextView
import androidx.core.view.isVisible

inline var TextView.textOrGone: CharSequence?
    get() = text
    set(value) {
        text = value
        isVisible = value.isNullOrBlank().not()
    }