package com.splanes.presentation.feature.dashboard

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.splanes.domain.feature.user.model.UserModel
import com.splanes.presentation.R
import com.splanes.presentation.common.base.BaseActivity
import com.splanes.presentation.common.base.adapter.SwipeToDismissCallback
import com.splanes.presentation.common.util.colorOf
import com.splanes.presentation.common.util.observe
import com.splanes.presentation.common.util.view.addOnScrollStateChanged
import com.splanes.presentation.databinding.ActivityDashboardBinding
import com.splanes.presentation.feature.dashboard.adapter.UserAdapter
import com.splanes.presentation.feature.detail.UserDetailActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DashboardActivity : BaseActivity<ActivityDashboardBinding>(
    ActivityDashboardBinding::inflate
) {

    private val viewModel: DashboardViewModel by viewModels()
    private lateinit var adapter: UserAdapter

    companion object {
        fun start(activity: BaseActivity<*>) {
            activity.startActivity(Intent(activity, DashboardActivity::class.java).apply {
                putExtras(bundleOf(EXTRA_NAVIGATION_TRANSITION to NavTransition.LATERAL))
            })
        }
    }

    override fun onBindUi() {
        actionBar?.setBackgroundDrawable(ColorDrawable(colorOf(R.color.primary)))
        initAdapter()
        initListeners()
    }

    override fun onSubscribeUi() {
        observe(viewModel.usersObservable) { adapter.update(it) }
    }

    override fun onLoadData() {
        viewModel.fetchUsers()
    }

    private fun initAdapter() {
        binding.usersRecyclerView.adapter =
            UserAdapter(mutableListOf(), ::onUserClick).also { adapter = it }
        ItemTouchHelper(SwipeToDismissCallback(adapter)).attachToRecyclerView(binding.usersRecyclerView)
    }

    private fun initListeners() {
        binding.loadButton.setOnClickListener { onLoadData() }
        binding.usersRecyclerView.addOnScrollStateChanged { newState ->
            binding.loadButton.animate()
                .alpha(if (newState == RecyclerView.SCROLL_STATE_IDLE) 1f else 0.4f)
        }
    }

    private fun onUserClick(model: UserModel) {
        UserDetailActivity.start(this, model)
    }
}