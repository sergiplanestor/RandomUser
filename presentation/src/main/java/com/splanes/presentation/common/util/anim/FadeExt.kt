package com.splanes.presentation.common.util.anim

import android.animation.TimeInterpolator
import android.view.View
import android.view.ViewPropertyAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.view.isVisible
import com.splanes.presentation.common.util.list.popFirst

const val FADE_ANIMATION_DEFAULT_DURATION = 500L

inline fun View.fadeInOut(
    isShowing: Boolean = isVisible.not(),
    duration: Long = FADE_ANIMATION_DEFAULT_DURATION,
    interpolator: TimeInterpolator = AccelerateDecelerateInterpolator(),
    startDelay: Long = 0,
    crossinline onStart: () -> Unit = {},
    crossinline onEnd: () -> Unit = {}
): ViewPropertyAnimator {
    val alphaStart = (if (isShowing) 0f else 1f).also { alpha = it }
    val alphaEnd = if (isShowing) 1f else 0f
    return this.animate().alpha(alphaEnd).apply {
        this.duration = duration
        this.interpolator = interpolator
        if (startDelay > 0) this.startDelay = startDelay
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