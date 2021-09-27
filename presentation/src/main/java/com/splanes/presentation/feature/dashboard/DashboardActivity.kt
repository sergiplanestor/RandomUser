package com.splanes.presentation.feature.dashboard

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.splanes.domain.feature.user.model.UserModel
import com.splanes.presentation.R
import com.splanes.presentation.common.base.BaseActivity
import com.splanes.presentation.common.base.adapter.SwipeToDismissCallback
import com.splanes.presentation.common.util.*
import com.splanes.presentation.common.util.view.addOnScrollStateChanged
import com.splanes.presentation.component.finder.FinderView
import com.splanes.presentation.databinding.ActivityDashboardBinding
import com.splanes.presentation.feature.dashboard.adapter.UserAdapter
import com.splanes.presentation.feature.detail.UserDetailActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DashboardActivity : BaseActivity<ActivityDashboardBinding>(
    ActivityDashboardBinding::inflate
) {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val viewModel: DashboardViewModel by viewModels()
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    lateinit var adapter: UserAdapter

    private var menu: Menu? = null

    companion object {
        fun start(activity: BaseActivity<*>) {
            activity.startActivity(Intent(activity, DashboardActivity::class.java).apply {
                putExtras(bundleOf(EXTRA_NAVIGATION_TRANSITION to NavTransition.LATERAL))
            })
        }
    }

    override fun onBindUi() {
        supportActionBar?.setBackgroundDrawable(ColorDrawable(colorOf(R.color.primary)))
        initAdapter()
        initListeners()
        binding.emptyStateView.bind(getString(R.string.dashboard_query_no_results))
    }

    override fun onSubscribeUi() {
        observe(viewModel.loaderObservable, binding.loaderView::updateVisibility)
        observe(viewModel.failureObservable) {
            showFeedback(isPositive = false, messageRes = it)
        }
        observe(viewModel.usersObservable) {
            adapter.update(it)
            updateCounter(it.size)
            onEmptyStateChanged(isShowing = it.isEmpty())
        }
    }

    override fun onLoadData() {
        viewModel.getUsers()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu
        menuInflater.inflate(R.menu.menu_dashboard, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_filter) {
            onFinderMenuClick()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initAdapter() {
        binding.usersRecyclerView.adapter = UserAdapter(
            mutableListOf(),
            ::onUserClick,
            ::onUserRemoved,
            ::onUserRemovedUndo
        ).also { adapter = it }
        ItemTouchHelper(SwipeToDismissCallback(adapter)).attachToRecyclerView(binding.usersRecyclerView)
        onEmptyStateChanged(isShowing = true)
    }

    private fun initListeners() {
        binding.finderView.bind(onSubmitQuery = ::onSubmitQuery, onClearQuery = ::onClearQuery)
        binding.loadButton.setOnClickListener { viewModel.fetchUsers() }
        binding.usersRecyclerView.addOnScrollStateChanged { newState ->
            binding.loadButton.animate()
                .alpha(if (newState == RecyclerView.SCROLL_STATE_IDLE) 1f else 0.4f)
        }
    }

    private fun onUserClick(model: UserModel) {
        UserDetailActivity.start(this, model)
    }

    private fun onUserRemoved(model: UserModel) {
        viewModel.removeUser(model)
        updateCounter(adapter.itemCount)
    }

    private fun onUserRemovedUndo(model: UserModel) {
        viewModel.insertUser(model)
        updateCounter(adapter.itemCount)
    }

    private fun onFinderMenuClick() {
        if (binding.finderView.isVisible) {
            binding.finderView.isVisible = false
            menu?.changeMenuIcon(R.id.menu_filter, drawableOf(R.drawable.ic_search_user).apply {
                applyTint(colorOf(R.color.white))
            })
        } else {
            binding.finderView.isVisible = true
            menu?.changeMenuIcon(R.id.menu_filter, drawableOf(R.drawable.ic_search_off).apply {
                applyTint(colorOf(R.color.red_error_background))
            })
        }
    }

    private fun onSubmitQuery(query: String, types: List<FinderView.QueryType>) {
        viewModel.applyQuery(query, types)
    }

    private fun onClearQuery() {
        viewModel.applyQuery("", emptyList())
    }

    private fun onEmptyStateChanged(isShowing: Boolean) {
        binding.userCountView.isVisible = isShowing.not()
        binding.usersRecyclerView.isVisible = isShowing.not()
        binding.emptyStateView.isVisible = isShowing
    }

    private fun updateCounter(count: Int) {
        binding.userCountView.text = getString(
            R.string.dashboard_display_user_count,
            count.toString()
        )
    }
}