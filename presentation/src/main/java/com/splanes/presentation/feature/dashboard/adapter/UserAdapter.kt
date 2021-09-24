package com.splanes.presentation.feature.dashboard.adapter

import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.splanes.domain.feature.user.model.UserModel
import com.splanes.presentation.component.user.OverviewUserView

class UserAdapter(
    private val items: MutableList<UserModel>,
    private val onClick: (UserModel) -> Unit,
    private val onSwipe: (UserModel) -> Unit
) : RecyclerView.Adapter<UserAdapter.Holder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =
        Holder(OverviewUserView(parent.context).apply {
            layoutParams = RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
            )
        })

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.view.bind(items[position], onClick, onSwipe)
    }

    override fun getItemCount(): Int = items.size

    fun update(items: List<UserModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    inner class Holder(val view: OverviewUserView) : RecyclerView.ViewHolder(view)
}