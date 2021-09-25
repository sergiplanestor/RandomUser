package com.splanes.presentation.common.util.anim

import android.animation.Animator
import android.view.ViewPropertyAnimator

@Suppress("UNCHECKED_CAST")
inline fun <T> ViewPropertyAnimator.applyListeners(
    crossinline onStart: () -> Unit = {},
    crossinline onEnd: () -> Unit = {},
    crossinline onCancel: () -> Unit = {},
    crossinline onRepeat: () -> Unit = {},
    noinline onValueUpdated: ((T) -> Unit)? = null
) =
    apply {
        setListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {
                onStart.invoke()
            }

            override fun onAnimationEnd(p0: Animator?) {
                onEnd.invoke()
            }

            override fun onAnimationCancel(p0: Animator?) {
                onCancel.invoke()
            }

            override fun onAnimationRepeat(p0: Animator?) {
                onRepeat.invoke()
            }
        })
        if (onValueUpdated != null) {
            setUpdateListener { (it.animatedValue as? T)?.let(onValueUpdated::invoke) }
        }
    }