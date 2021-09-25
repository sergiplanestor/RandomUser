package com.splanes.presentation.feature.detail

import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.view.MenuItem
import android.view.View
import androidx.core.os.bundleOf
import com.splanes.domain.feature.user.model.UserGender
import com.splanes.domain.feature.user.model.UserModel
import com.splanes.presentation.R
import com.splanes.presentation.common.base.BaseActivity
import com.splanes.presentation.common.util.colorOf
import com.splanes.presentation.common.util.drawableOf
import com.splanes.presentation.common.util.scope.runDelayed
import com.splanes.presentation.common.util.view.loadUrl
import com.splanes.presentation.databinding.ActivityUserDetailsBinding


class UserDetailActivity :
    BaseActivity<ActivityUserDetailsBinding>(ActivityUserDetailsBinding::inflate) {

    companion object {

        private const val WINDOW_BACKGROUND_DELAY = 400L
        private const val USER = "USER"

        fun start(activity: BaseActivity<*>, user: UserModel) {
            activity.startActivity(
                Intent(
                    activity,
                    UserDetailActivity::class.java
                ).apply {
                    putExtras(
                        bundleOf(
                            USER to user,
                            EXTRA_NAVIGATION_TRANSITION to NavTransition.MODAL
                        )
                    )
                }
            )
        }
    }

    private val user: UserModel? by lazy { intent.extras?.getParcelable(USER) }

    override fun onBindUi() {
        user?.run(::bindUser)
        binding.closeButton.setOnClickListener { onBackPressed() }
        updateWindowBackground(isShowing = true)
    }

    override fun onBackPressed() {
        updateWindowBackground(isShowing = false)
        super.onBackPressed()
    }

    private fun bindUser(user: UserModel) {
        binding.userImgView.loadUrl(
            user.imageLargeUrl,
            isCircular = false,
            error = R.drawable.ic_user_placeholder
        )
        binding.userCompleteNameView.text = user.completeNameWithTitle
        binding.userGenderView.setText(resolveGenderNameRes(user.gender))
        binding.userGenderView.setCompoundDrawablesWithIntrinsicBounds(
            resolveGenderIconRes(user.gender)?.let(::drawableOf),
            null,
            null,
            null
        )
        binding.userEmailView.text = user.email
        binding.userAddressView.text = user.address
        binding.userRegisteredDateView.text = user.registeredOn.formatted
    }

    private fun resolveGenderNameRes(gender: UserGender): Int =
        when (gender) {
            UserGender.MALE -> R.string.user_gender_male
            UserGender.FEMALE -> R.string.user_gender_female
            UserGender.UNKNOWN -> R.string.user_gender_unknown
        }

    private fun resolveGenderIconRes(gender: UserGender): Int? =
        when (gender) {
            UserGender.MALE -> R.drawable.ic_gender_male
            UserGender.FEMALE -> R.drawable.ic_gender_female
            UserGender.UNKNOWN -> null
        }

    private fun updateWindowBackground(isShowing: Boolean) {
        val block = {
            window.setBackgroundDrawable(
                ColorDrawable(
                    colorOf(
                        if (isShowing) {
                            R.color.black_opacity_50
                        } else {
                            android.R.color.transparent
                        }
                    )
                )
            )
        }
        if (isShowing) runDelayed(WINDOW_BACKGROUND_DELAY) { block() } else block()
    }
}