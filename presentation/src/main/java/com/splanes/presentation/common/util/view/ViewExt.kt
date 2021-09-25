package com.splanes.presentation.common.util.view

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.children

inline val View?.decorView: FrameLayout?
    get() {
        var view = this
        do {
            if (view is FrameLayout && view.id == android.R.id.content) {
                return view
            } else {
                view = view?.parent as? View
            }
        } while (view != null)
        return null
    }

inline val View?.snackBarSuitableParent: ViewGroup?
    get() = decorView?.children?.findLast { it is CoordinatorLayout } as? CoordinatorLayout ?: decorView