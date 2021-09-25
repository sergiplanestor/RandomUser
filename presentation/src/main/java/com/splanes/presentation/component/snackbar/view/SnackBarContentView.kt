package com.splanes.presentation.component.snackbar.view

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import com.google.android.material.snackbar.ContentViewCallback
import com.splanes.presentation.common.util.anim.fadeInOut
import com.splanes.presentation.common.util.colorOf
import com.splanes.presentation.common.util.colorStateListOf
import com.splanes.presentation.common.util.drawableOf
import com.splanes.presentation.common.util.view.setTextOrGone
import com.splanes.presentation.common.util.viewbinding.inflateViewBinding
import com.splanes.presentation.component.snackbar.model.SnackBarModel
import com.splanes.presentation.databinding.ComponentSnackBarContentViewBinding

class SnackBarContentView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ContentViewCallback {

    private val binding = inflateViewBinding(ComponentSnackBarContentViewBinding::inflate)

    fun bind(model: SnackBarModel) {
        binding.cardView.setCardBackgroundColor(context.colorOf(model.backgroundColorRes))
        binding.iconMessageView.run {
            this.setImageDrawable(context.drawableOf(model.iconRes))
            this.imageTintList = context.colorStateListOf(model.iconTintRes)
        }
        binding.messageView.setText(model.message)
        binding.actionView.run {
            setTextOrGone(model.action)
            setOnClickListener { model.onActionClick?.invoke() }
        }
        if (model.action == null) updateMessageViewLayoutParams()
    }

    override fun animateContentOut(delay: Int, duration: Int) {
        animateContent(delay.toLong(), duration.toLong(), isShowing = false)
    }

    override fun animateContentIn(delay: Int, duration: Int) {
        animateContent(delay.toLong(), duration.toLong(), isShowing = true)
    }

    private fun updateMessageViewLayoutParams() {
        binding.messageView.updateLayoutParams<LayoutParams> {
            endToStart = LayoutParams.UNSET
            endToEnd = LayoutParams.PARENT_ID
        }
    }

    private fun animateContent(delay: Long, duration: Long, isShowing: Boolean) {
        binding.contentLayout.fadeInOut(
            isShowing = isShowing,
            duration = duration,
            startDelay = delay
        )
    }
}