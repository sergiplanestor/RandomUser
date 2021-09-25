package com.splanes.presentation.common.base.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.splanes.presentation.R
import com.splanes.presentation.component.snackbar.view.SnackBar
import com.splanes.presentation.component.snackbar.model.SnackBarModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class DiffUtilAdapter<T, V : View>(open val items: MutableList<T>) :
    RecyclerView.Adapter<ViewWrapper<V>>() {

    protected var recentlyRemoved: Pair<Int, T>? = null
    protected open val undoSnackBarModel: SnackBarModel =
        SnackBarModel.buildGeneric(
            iconRes = R.drawable.ic_undo,
            message = R.string.dashboard_undo_item_removed_msg,
            action = R.string.action_undo,
            duration = SnackBar.Duration.Custom(3000),
            onActionClick = ::performUndoRemoved
        )


    private var recyclerView: RecyclerView? = null

    // Abstract methods

    abstract fun onCreateItemView(parent: ViewGroup, viewType: Int): V

    abstract fun onBindView(view: V, position: Int)

    abstract fun areItemsTheSame(oldItem: T, newItem: T): Boolean

    abstract fun areContentsTheSame(oldItem: T, newItem: T): Boolean

    // RecyclerView.Adapter methods

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewWrapper<V> =
        ViewWrapper(onCreateItemView(parent, viewType))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewWrapper<V>, position: Int) =
        onBindView(holder.view, position)

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        this.recyclerView = null
    }

    // Public (and overridable) methods

    open fun update(newData: List<T>) {
        if (newData.isEmpty()) {
            items.clear()
            notifyDataSetChanged()
        } else {
            CoroutineScope(Dispatchers.Main).launch {
                val diffResult = calculateDiffResult(newData)
                items.clear()
                items.addAll(newData)
                diffResult.dispatchUpdatesTo(this@DiffUtilAdapter)
            }
        }
    }

    open fun remove(position: Int) {
        recentlyRemoved = position to items[position]
        items.removeAt(position)
        recyclerView?.scrollToPosition(position)
        notifyItemRemoved(position)
        showUndoRemoved()
    }

    protected open fun showUndoRemoved() {
        recyclerView?.let { SnackBar.show(it, undoSnackBarModel) } ?: run { recentlyRemoved = null }
    }

    protected open fun performUndoRemoved() {
        recentlyRemoved?.let {
            items.add(it.first, it.second)
            recyclerView?.scrollToPosition(it.first)
            notifyItemInserted(it.first)
            recentlyRemoved = null
        }
    }

    // DiffUtil methods
    private suspend fun calculateDiffResult(newData: List<T>): DiffUtil.DiffResult =
        withContext(Dispatchers.IO) {
            DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    if (oldItemPosition <= items.lastIndex && newItemPosition <= newData.lastIndex) {
                        return areItemsTheSame(items[oldItemPosition], newData[newItemPosition])
                    }
                    return false
                }

                override fun getOldListSize() = items.size

                override fun getNewListSize() = newData.size

                override fun areContentsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int
                ): Boolean {
                    if (oldItemPosition <= items.lastIndex && newItemPosition <= newData.lastIndex) {
                        return areContentsTheSame(items[oldItemPosition], newData[newItemPosition])
                    }
                    return false
                }

                override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
                    return getAdapterChangePayload(oldItemPosition, newItemPosition)
                }
            })
        }

    protected open fun getAdapterChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return null
    }

}