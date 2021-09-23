package com.splanes.presentation.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.splanes.presentation.common.util.InflateViewBindingActivity
import com.splanes.presentation.common.util.inflateViewBinding

abstract class BaseActivity<VB : ViewBinding>(
    inflate: InflateViewBindingActivity<VB>
) : AppCompatActivity() {

    protected val binding: VB by lazy { inflateViewBinding(inflate) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onSubscribeUi()
        onBindUi()
    }

    protected open fun onBindUi() {
        // Nothing to do here
    }

    protected open fun onSubscribeUi() {
        // Nothing to do here
    }

}