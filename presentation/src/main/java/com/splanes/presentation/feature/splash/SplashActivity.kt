package com.splanes.presentation.feature.splash

import android.os.Handler
import android.os.Looper
import android.view.View
import com.splanes.presentation.common.base.BaseActivity
import com.splanes.presentation.common.util.fadeInOutCascade
import com.splanes.presentation.databinding.ActivitySplashBinding

class SplashActivity : BaseActivity<ActivitySplashBinding>(
    ActivitySplashBinding::inflate
) {

    companion object {
        private const val WELCOME_VIEW_ALPHA_DURATION = 500L
        private const val TITLE_VIEW_ALPHA_DURATION = 1000L
        private const val LOTTIE_VIEW_ALPHA_DURATION = 2000L
        private const val TRANSITION_DELAY = 500L
    }

    private val animatedViews: List<View> by lazy {
        listOf(
            binding.welcomeView,
            binding.titleView,
            binding.animationView
        )
    }

    override fun onResume() {
        super.onResume()
        animatedViews.fadeInOutCascade(
            durations = listOf(
                WELCOME_VIEW_ALPHA_DURATION,
                TITLE_VIEW_ALPHA_DURATION,
                LOTTIE_VIEW_ALPHA_DURATION
            ),
            onEnd = {
                Handler(Looper.getMainLooper()).postDelayed(
                    {
                        // TODO: Nav to dashboard
                    },
                    TRANSITION_DELAY
                )
            }

        )
    }

}