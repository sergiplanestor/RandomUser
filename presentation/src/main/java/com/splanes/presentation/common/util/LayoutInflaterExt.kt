package com.splanes.presentation.common.util

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment

inline val View.inflater: LayoutInflater get() =
    LayoutInflater.from(context)

inline val Activity.inflater: LayoutInflater get() =
    layoutInflater

inline val Fragment.inflater: LayoutInflater get() =
    layoutInflater