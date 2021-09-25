package com.splanes.presentation.component.user

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.splanes.domain.feature.user.model.UserModel
import com.splanes.presentation.common.util.view.textOrGone
import com.splanes.presentation.common.util.view.thumbnail
import com.splanes.presentation.common.util.viewbinding.inflateViewBinding
import com.splanes.presentation.databinding.ComponentOverviewUserViewBinding

class OverviewUserView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = inflateViewBinding(ComponentOverviewUserViewBinding::inflate)

    fun bind(model: UserModel, onClick: (UserModel) -> Unit, onRemoveClick: () -> Unit) {
        binding.userImgView.thumbnail(model.thumbnailUrl)
        binding.userCompleteNameView.textOrGone = model.completeName
        binding.userEmailView.textOrGone = model.email
        binding.userPhoneView.textOrGone = model.mobile
        binding.buttonDelete.setOnClickListener { onRemoveClick.invoke() }
        binding.root.setOnClickListener { onClick.invoke(model) }
    }

}