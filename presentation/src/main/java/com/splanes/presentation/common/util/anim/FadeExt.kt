package com.splanes.presentation.common.util.anim

import android.animation.TimeInterpolator
import android.view.View
import android.view.ViewPropertyAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.view.isVisible
import com.splanes.presentation.common.util.list.removeFirst

const val FADE_ANIMATION_DEFAULT_DURATION = 500L

inline fun View.fadeInOut(
    isShowing: Boolean = isVisible.not(),
    duration: Long = FADE_ANIMATION_DEFAULT_DURATION,
    interpolator: TimeInterpolator = AccelerateDecelerateInterpolator(),
    startDelay: Long = 0,
    crossinline onStart: () -> Unit = {},
    crossinline onCancel: () -> Unit = {},
    crossinline onEnd: () -> Unit = {},
    noinline onUpdated: ((Float) -> Unit)? = null,
): ViewPropertyAnimator {
    val alphaAtStart = (if (isShowing) 0f else 1f).also { alpha = it }
    return this.animate().alpha(1f - alphaAtStart).apply {
        this.duration = duration
        this.interpolator = interpolator
        if (startDelay > 0) this.startDelay = startDelay
        applyListeners(
            onStart = {
                if (isShowing) isVisible = true
                onStart()
            },
            onEnd = {
                if (!isShowing) isVisible = false
                onEnd()
            },
            onCancel = onCancel,
            onValueUpdated = onUpdated
        )
    }.also { it.start() }
}

fun List<View>.fadeInOutCascade(
    isShowing: List<Boolean> = map { it.isVisible.not() },
    durations: List<Long> = map { FADE_ANIMATION_DEFAULT_DURATION },
    interpolator: List<TimeInterpolator> = map { AccelerateDecelerateInterpolator() },
    onStart: () -> Unit = {},
    onEnd: () -> Unit = {}
) {
    when {
        size != isShowing.size -> {
            throw IllegalArgumentException(
                "`List<View>` has not the same size than `isShowing`"
            )
        }
        size != durations.size -> {
            throw IllegalArgumentException(
                "`List<View>` has not the same size than `durations`"
            )
        }
        size != interpolator.size -> {
            throw IllegalArgumentException(
                "`List<View>` has not the same size than `interpolator`"
            )
        }
    }
    firstOrNull()?.fadeInOut(
        isShowing = isShowing.first(),
        duration = durations.first(),
        interpolator = interpolator.first(),
        onStart = onStart,
        onEnd = {
            if (size > 1) {
                removeFirst().fadeInOutCascade(
                    isShowing = isShowing.removeFirst(),
                    durations = durations.removeFirst(),
                    interpolator = interpolator.removeFirst(),
                    onEnd = onEnd
                )
            } else {
                onEnd()
            }
        }
    )
}