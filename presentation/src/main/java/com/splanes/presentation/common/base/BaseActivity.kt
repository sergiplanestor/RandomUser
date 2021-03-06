package com.splanes.presentation.common.base

import android.content.Intent
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.splanes.presentation.R
import com.splanes.presentation.common.util.viewbinding.InflateViewBindingActivity
import com.splanes.presentation.common.util.viewbinding.inflateViewBinding
import com.splanes.presentation.component.snackbar.model.SnackBarModel
import com.splanes.presentation.component.snackbar.view.SnackBar

abstract class BaseActivity<VB : ViewBinding>(
    inflate: InflateViewBindingActivity<VB>
) : AppCompatActivity() {

    protected enum class NavTransition {
        LATERAL,
        MODAL
    }

    companion object {
        const val EXTRA_NAVIGATION_TRANSITION = "nav.transition"
    }

    protected val binding: VB by lazy { inflateViewBinding(inflate) }
    private var isFirstTime: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        onSubscribeUi()
        onBindUi()
    }

    override fun onResume() {
        super.onResume()
        if (isFirstTime) {
            onLoadData()
            isFirstTime = false
        }
    }

    override fun onStop() {
        super.onStop()
        onSaveChanges()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overrideTransition()
    }

    override fun startActivity(intent: Intent?) {
        val anim = getNavAnimations(intent)
        super.startActivity(intent)
        overridePendingTransition(anim.first, anim.second)
    }

    protected open fun onBindUi() {
        // Nothing to do here
    }

    protected open fun onSubscribeUi() {
        // Nothing to do here
    }

    protected open fun onLoadData() {
        // Nothing to do here
    }

    protected open fun onSaveChanges() {
        // Nothing to do here
    }

    protected open fun showFeedback(
        isPositive: Boolean,
        isIndefinite: Boolean = false,
        @StringRes messageRes: Int,
        duration: Int = 5000
    ) {
        SnackBar.show(
            binding.root,
            SnackBarModel.buildResult(
                isPositive = isPositive,
                message = messageRes,
                duration = if (isIndefinite) {
                    SnackBar.Duration.Indefinite
                } else {
                    SnackBar.Duration.Custom(duration)
                }
            )
        )
    }

    private fun overrideTransition() {
        val anim = getNavAnimations(intent, isStart = false)
        overridePendingTransition(anim.first, anim.second)
    }

    private fun getNavAnimations(intent: Intent?, isStart: Boolean = true): Pair<Int, Int> {
        val bundle = intent?.extras
        return when (bundle?.getSerializable(EXTRA_NAVIGATION_TRANSITION) as NavTransition?) {
            NavTransition.LATERAL ->
                if (isStart) {
                    bundle?.putSerializable(EXTRA_NAVIGATION_TRANSITION, NavTransition.LATERAL)
                    Pair(R.anim.slide_in_right, R.anim.slide_out_left)
                } else {
                    Pair(R.anim.slide_in_left, R.anim.slide_out_right)
                }
            NavTransition.MODAL ->
                if (isStart) {
                    bundle?.putSerializable(EXTRA_NAVIGATION_TRANSITION, NavTransition.MODAL)
                    Pair(R.anim.slide_down, R.anim.hold)
                } else {
                    Pair(R.anim.hold, R.anim.slide_up)
                }
            else -> Pair(0, 0)
        }
    }
}