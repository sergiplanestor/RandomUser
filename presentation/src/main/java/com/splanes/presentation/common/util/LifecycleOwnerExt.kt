package com.splanes.presentation.common.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

inline fun <T> LifecycleOwner.observe(liveData: LiveData<T>, crossinline block: (T) -> Unit) {
    liveData.observe(this) { block(it) }
}