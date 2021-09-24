package com.splanes.presentation.common.util

import android.animation.TimeInterpolator
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.view.isVisible

const val FADE_ANIMATION_DEFAULT_DURATION = 500L

inline fun View.fadeInOut(
    isShowing: Boolean = isVisible.not(),
    duration: Long = FADE_ANIMATION_DEFAULT_DURATION,
    interpolator: TimeInterpolator = AccelerateDecelerateInterpolator(),
    crossinline onStart: () -> Unit = {},
    crossinline onEnd: () -> Unit = {}
) {
    val alphaStart = (if (isShowing) 0f else 1f).also { alpha = it }
    val alphaEnd = if (isShowing) 1f else 0f
    this.animate().alpha(alphaEnd).apply {
        setDuration(duration)
        setInterpolator(interpolator)
        setUpdateListener {
            when (it.animatedValue as Float) {
                alphaStart -> {
                    if (isShowing) isVisible = true
                    onStart()
                }
                alphaEnd -> {
                    if (!isShowing) isVisible = false
                    onEnd()
                }
            }
        }
    }.start()
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
                popFirst().fadeInOutCascade(
                    isShowing = isShowing.popFirst(),
                    durations = durations.popFirst(),
                    interpolator = interpolator.popFirst(),
                    onEnd = onEnd
                )
            } else {
                onEnd()
            }
        }
    )
}