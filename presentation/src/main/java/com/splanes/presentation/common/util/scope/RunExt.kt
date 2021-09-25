package com.splanes.presentation.common.util.scope

import android.os.Handler
import android.os.Looper

inline fun <T> T.runDelayed(delay: Long, crossinline block: T.() -> Unit) {
    Handler(Looper.myLooper() ?: Looper.getMainLooper()).postDelayed({ block() }, delay)
}