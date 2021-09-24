package com.splanes.presentation.common.util

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.splanes.presentation.R

fun ImageView.thumbnail(url: String) {
    loadUrl(
        url = url,
        isCircular = true,
        placeholder = R.drawable.ic_user_placeholder,
        error = R.drawable.ic_user_placeholder,
    )
}

fun ImageView.loadUrl(
    url: String,
    isCircular: Boolean,
    @DrawableRes placeholder: Int? = null,
    @DrawableRes error: Int? = null
) {
    Glide
        .with(context)
        .load(url)
        .run { if(isCircular) apply(RequestOptions.circleCropTransform()) else this }
        .run { placeholder?.let { placeholder(it) } ?: this }
        .run { error?.let { error(it) } ?: this }
        .into(this)
}