package com.splanes.presentation.feature.dashboard.adapter

import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.splanes.domain.feature.user.model.UserModel
import com.splanes.presentation.common.base.adapter.DiffUtilAdapter
import com.splanes.presentation.common.util.anim.bounce
import com.splanes.presentation.component.user.OverviewUserView

class UserAdapter(
    override val items: MutableList<UserModel>,
    private val onClick: (UserModel) -> Unit,
    private val onRemove: (UserModel) -> Unit,
    private val onUndoRemove: (UserModel) -> Unit
) : DiffUtilAdapter<UserModel, OverviewUserView>(items) {

    companion object {
        const val ANIM_DELAY = 1000L
    }

    private var isAnimationDone: Boolean = false

    override fun onCreateItemView(parent: ViewGroup, viewType: Int): OverviewUserView =
        OverviewUserView(parent.context).apply {
            layoutParams = RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
            )
        }

    override fun onBindView(view: OverviewUserView, position: Int) {
        view.bind(
            items[position],
            onClick,
            onRemoveClick = { remove(position) }
        )
        if (!isAnimationDone && position == 0) {
            view.bounce(ANIM_DELAY)
            isAnimationDone = true
        }
    }

    override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean =
        oldItem == newItem

    override fun update(newData: List<UserModel>) {
        isAnimationDone = false
        super.update(newData)
    }

    override fun remove(position: Int) {
        val model = items[position]
        super.remove(position)
        onRemove.invoke(model)
    }

    override fun performUndoRemoved() {
        val model = recentlyRemoved?.second
        super.performUndoRemoved()
        model?.let(onUndoRemove::invoke)
    }
}