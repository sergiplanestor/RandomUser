package com.splanes.presentation.component.emptystate

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.splanes.presentation.common.util.view.textOrGone
import com.splanes.presentation.common.util.viewbinding.inflateViewBinding
import com.splanes.presentation.databinding.ComponentEmptyStateViewBinding

class EmptyStateView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = inflateViewBinding(ComponentEmptyStateViewBinding::inflate)

    fun bind(message: String?) {
        binding.emptyStateTextView.textOrGone = message
    }

    override fun setVisibility(visibility: Int) {
        super.setVisibility(visibility)
        if (visibility == View.VISIBLE) {
            binding.emptyStateAnimView.playAnimation()
        } else {
            binding.emptyStateAnimView.cancelAnimation()
        }
    }
}