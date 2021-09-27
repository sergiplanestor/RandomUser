package com.splanes.randomuser.presentation.feature.dashboard

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.filters.LargeTest
import com.google.common.truth.Truth
import com.splanes.presentation.R
import com.splanes.presentation.feature.dashboard.DashboardActivity
import com.splanes.randomuser.MainCoroutineRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.withTimeout
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.coroutines.resume
import androidx.test.espresso.matcher.ViewMatchers.*
import junit.framework.AssertionFailedError


@ExperimentalCoroutinesApi
@LargeTest
@HiltAndroidTest
class DashboardInteractionsTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var activityScenarioRule = activityScenarioRule<DashboardActivity>()

    @get:Rule(order = 2)
    val coroutineRule = MainCoroutineRule()

    @Rule(order = 3)
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initialize() {
        hiltRule.inject()
    }

    @Test
    fun givenDashboardActivity_whenAddUsersButtonIsPressed_thenUsersObservableIsNotified() {
        val timeout = 3000L // 3s
        activityScenarioRule.scenario.onActivity { activity ->
            runBlockingTest {
                onView(withId(R.id.load_button)).perform(click())
                val result = runCatching {
                    withTimeout(timeout) {
                        suspendCancellableCoroutine<Boolean> { continuation ->
                            activity.viewModel.usersObservable.observeForever {
                                continuation.resume(value = true)
                            }
                        }
                    }
                }.getOrDefault(defaultValue = false)
                Truth.assertThat(result).isTrue()
            }
        }
    }

    @Test
    fun givenDashboardActivity_whenSearchMenuIsPressed_thenFinderViewVisibilityChanges() {
        onView(withId(R.id.finder_view)).assertVisibility(isVisible = false)
        onView(withId(R.id.menu_filter)).perform(click())
        onView(withId(R.id.finder_view)).assertVisibility(isVisible = true)
        onView(withId(R.id.menu_filter)).perform(click())
        onView(withId(R.id.finder_view)).assertVisibility(isVisible = false)
    }

    private fun ViewInteraction.assertVisibility(isVisible: Boolean) {
        if (isVisible) {
            check(matches(isDisplayed()))
        } else {
            check { view, noViewFoundException ->
                view?.run { if (visibility == View.VISIBLE) throw AssertionError() }
                    ?: throw noViewFoundException
            }
        }

    }
}