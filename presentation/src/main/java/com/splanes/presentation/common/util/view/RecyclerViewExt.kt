package com.splanes.presentation.common.util.view

import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.addOnScrollStateChanged(block: (newState: Int) -> Unit) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            block.invoke(newState)
        }
    })
}