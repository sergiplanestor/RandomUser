package com.splanes.presentation.common.util

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.Menu
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.splanes.presentation.common.util.primitive.remove

inline val Int.dp: Int
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics
    ).toInt()

fun Context.colorOf(@ColorRes resource: Int): Int =
    ContextCompat.getColor(this, resource)

fun Context.colorOf(hex: String?): Int? =
    runCatching { Color.parseColor(hex?.remove("#")) }.getOrNull()

fun Context.colorStateListOf(@ColorRes resource: Int): ColorStateList =
    ColorStateList.valueOf(colorOf(resource))

fun Context.drawableOf(@DrawableRes resource: Int): Drawable? =
    runCatching { ContextCompat.getDrawable(this, resource) }.getOrNull()

fun Context.dimenOf(@DimenRes resource: Int): Int =
    resources.getDimension(resource).toInt()

fun Drawable?.applyTint(colorInt: Int) = this?.setTintList(ColorStateList.valueOf(colorInt))

fun Menu.changeMenuIcon(menuItemResId: Int, drawable: Drawable?) =
    drawable?.let { findItem(menuItemResId)?.setIcon(drawable) }