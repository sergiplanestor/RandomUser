package com.splanes.presentation.feature.dashboard.adapter

import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.splanes.domain.feature.user.model.UserModel
import com.splanes.presentation.common.base.adapter.DiffUtilAdapter
import com.splanes.presentation.component.user.OverviewUserView

class UserAdapter(
    override val items: MutableList<UserModel>,
    private val onClick: (UserModel) -> Unit,
    private val onSwipe: (UserModel) -> Unit
) : DiffUtilAdapter<UserModel, OverviewUserView>(items) {

    override fun onCreateItemView(parent: ViewGroup, viewType: Int): OverviewUserView =
        OverviewUserView(parent.context).apply {
            layoutParams = RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
            )
        }

    override fun onBindView(view: OverviewUserView, position: Int) {
        view.bind(items[position], onClick, onSwipe)
    }

    override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean =
        oldItem == newItem
}