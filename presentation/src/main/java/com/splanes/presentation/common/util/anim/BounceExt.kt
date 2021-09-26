package com.splanes.presentation.common.util.anim

import android.animation.ObjectAnimator
import android.view.View
import androidx.core.animation.addListener
import com.splanes.presentation.common.util.list.removeFirst

private const val X_ON_START = 0f
private const val X_BOUNCE_MEDIUM = 40f
private const val X_BOUNCE_MIN_1 = 30f
private const val X_BOUNCE_MIN_2 = 15f

fun View.objectAnimOfFloat(property: String, vararg values: Float) : ObjectAnimator =
    ObjectAnimator.ofFloat(this, property, *values)

fun View.bounce(startDelay: Long = 0, iteration: Int = 0) {
    var list = mutableListOf(
        X_BOUNCE_MEDIUM,
        X_BOUNCE_MIN_1,
        X_BOUNCE_MIN_2,
        X_BOUNCE_MIN_2
    )
    for (i in 0 until iteration) {
        list = list.removeFirst().toMutableList()
    }
    objectAnimOfFloat(property = "translationX", X_ON_START, list.first(), X_ON_START).apply {
        if (startDelay > 0 && list.size == 4) this.startDelay = startDelay
        this.duration = (2000 / (list.size * 2)).toLong()
        this.addListener(
            onEnd = {
                if (list.size > 1) {
                    bounce(startDelay, iteration + 1)
                }
            }
        )
    }.start()
}