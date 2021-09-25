package com.splanes.presentation.component.snackbar.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.IntDef
import androidx.annotation.StringRes
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.splanes.presentation.R

class SnackBarModel private constructor(
    @DrawableRes val iconRes: Int,
    @ColorRes val iconTintRes: Int,
    @StringRes val message: Int,
    @StringRes val action: Int? = null,
    @ColorRes val backgroundColorRes: Int,
    @BaseTransientBottomBar.Duration val duration: Int,
    val onActionClick: (() -> Unit)? = null,
    val onClick: () -> Unit = {},
    val onDismiss: () -> Unit = {}
) {

    companion object {

        fun buildGeneric(
            @DrawableRes iconRes: Int,
            @ColorRes iconTintRes: Int = R.color.text_primary,
            @ColorRes backgroundColorRes: Int = R.color.secondary_light,
            @StringRes message: Int,
            @StringRes action: Int? = null,
            @BaseTransientBottomBar.Duration duration: Int = BaseTransientBottomBar.LENGTH_SHORT,
            onActionClick: (() -> Unit)? = null,
            onClick: () -> Unit = {},
            onDismiss: () -> Unit = {}
        ): SnackBarModel =
            SnackBarModel(
                iconRes = iconRes,
                iconTintRes = iconTintRes,
                backgroundColorRes = backgroundColorRes,
                message = message,
                action = action,
                duration = duration,
                onActionClick = onActionClick,
                onClick = onClick,
                onDismiss = onDismiss
            )

        fun buildResult(
            isPositive: Boolean,
            @StringRes message: Int,
            @StringRes action: Int? = null,
            @BaseTransientBottomBar.Duration duration: Int = BaseTransientBottomBar.LENGTH_LONG,
            onActionClick: (() -> Unit)? = null,
            onClick: () -> Unit = {},
            onDismiss: () -> Unit = {}
        ): SnackBarModel =
            buildGeneric(
                iconRes = isPositive.iconResByResult(),
                iconTintRes = isPositive.iconTintResByResult(),
                backgroundColorRes = isPositive.backgroundResByResult(),
                message = message,
                action = action,
                duration = duration,
                onActionClick = onActionClick,
                onClick = onClick,
                onDismiss = onDismiss
            )

        private fun Boolean.iconResByResult(): Int =
            if (this) R.drawable.ic_success_rounded else R.drawable.ic_error

        private fun Boolean.iconTintResByResult(): Int =
            if (this) R.color.green_success_accent else R.color.red_error_accent

        private fun Boolean.backgroundResByResult(): Int =
            if (this) R.color.green_success_background else R.color.red_error_background
    }
}



