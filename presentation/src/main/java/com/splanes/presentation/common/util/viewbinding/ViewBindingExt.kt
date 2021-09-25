package com.splanes.presentation.common.util.viewbinding

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.splanes.presentation.common.util.inflater

typealias InflateViewBindingComponent<VB> = (LayoutInflater, ViewGroup?, Boolean) -> VB

typealias InflateViewBindingActivity<VB> = (LayoutInflater) -> VB

typealias InflateViewBindingFragment<VB> = (LayoutInflater, ViewGroup?, Boolean) -> VB

fun <VB : ViewBinding> View.inflateViewBinding(
    inflate: InflateViewBindingComponent<VB>,
    parent: ViewGroup? = this as? ViewGroup,
    attachToParent: Boolean = true
): VB =
    inflate.invoke(inflater, parent, attachToParent)

fun <VB : ViewBinding> Activity.inflateViewBinding(
    inflate: InflateViewBindingActivity<VB>
): VB =
    inflate.invoke(inflater)

fun <VB : ViewBinding> Fragment.inflateViewBinding(
    inflate: InflateViewBindingFragment<VB>,
    container: ViewGroup?
): VB =
    inflate.invoke(inflater, container, false)
