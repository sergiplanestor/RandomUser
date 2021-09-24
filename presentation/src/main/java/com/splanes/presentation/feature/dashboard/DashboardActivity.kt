package com.splanes.presentation.feature.dashboard

import android.content.Intent
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import com.splanes.domain.feature.user.model.UserModel
import com.splanes.presentation.common.base.BaseActivity
import com.splanes.presentation.common.util.observe
import com.splanes.presentation.databinding.ActivityDashboardBinding
import com.splanes.presentation.feature.dashboard.adapter.UserAdapter
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
        super.onBindUi()
        binding.usersRecyclerView.adapter =
            UserAdapter(mutableListOf(), ::onUserClick, ::onUserSwipe).also { adapter = it }
    }

    override fun onSubscribeUi() {
        observe(viewModel.usersObservable) { adapter.update(it) }
    }

    override fun onLoadData() {
        viewModel.fetchUsers()
    }

    private fun onUserClick(model: UserModel) {

    }

    private fun onUserSwipe(model: UserModel) {

    }
}