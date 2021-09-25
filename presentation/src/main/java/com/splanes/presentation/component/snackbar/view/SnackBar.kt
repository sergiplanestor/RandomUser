package com.splanes.presentation.component.snackbar.view

import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.splanes.presentation.common.util.dp
import com.splanes.presentation.common.util.view.snackBarSuitableParent
import com.splanes.presentation.component.snackbar.model.SnackBarModel

class SnackBar(
    parent: ViewGroup,
    content: SnackBarContentView
) : BaseTransientBottomBar<SnackBar>(parent, content, content) {

    sealed class Duration(open val value: Int) {
        object Indefinite : Duration(LENGTH_INDEFINITE)
        object Short : Duration(LENGTH_SHORT)
        object Long : Duration(LENGTH_LONG)
        data class Custom(val millis: Int): Duration(millis)
    }

    init {
        getView().apply {
            setBackgroundColor(context.getColor(android.R.color.transparent))
            setPadding(0, 0, 0, PADDING_BOTTOM_DP.dp)
        }
    }

    companion object {

        private const val PADDING_BOTTOM_DP = 20

        fun show(view: View?, model: SnackBarModel) {

            val viewGroup = view.snackBarSuitableParent ?: return

            val content = SnackBarContentView(viewGroup.context).apply { bind(model) }

            SnackBar(viewGroup, content).apply {
                duration = model.duration.value
                content.setOnClickListener {
                    model.onClick.invoke()
                    dismiss()
                }
            }.show()

            Handler(Looper.getMainLooper()).postDelayed(
                { model.onDismiss.invoke() },
                model.duration.value.toLong()
            )
        }
    }
}