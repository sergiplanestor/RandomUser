package com.splanes.presentation.component.loader

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.splanes.presentation.common.util.anim.fadeInOut
import com.splanes.presentation.common.util.viewbinding.inflateViewBinding
import com.splanes.presentation.databinding.ComponentLoaderViewBinding

class LoaderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    companion object {
        const val VISIBILITY_ANIM_DURATION = 125L
    }

    private val binding = inflateViewBinding(ComponentLoaderViewBinding::inflate)

    fun show() {
        updateVisibility(isShowing = true)
    }

    fun hide() {
        updateVisibility(isShowing = false)
    }

    fun updateVisibility(isShowing: Boolean) {
        fadeInOut(
            isShowing = isShowing,
            duration = VISIBILITY_ANIM_DURATION,
            onStart = { if (isShowing) binding.loaderAnimView.playAnimation() },
            onEnd = { if (!isShowing) binding.loaderAnimView.cancelAnimation() },
            onCancel = { isVisible = false }
        )
    }
}