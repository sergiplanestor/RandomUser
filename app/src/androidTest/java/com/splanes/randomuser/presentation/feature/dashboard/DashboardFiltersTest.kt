package com.splanes.randomuser.presentation.feature.dashboard

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.splanes.domain.common.net.Response
import com.splanes.domain.feature.user.model.UserModel
import com.splanes.domain.feature.user.usecase.FetchUsersUseCase
import com.splanes.domain.feature.user.usecase.GetUsersUseCase
import com.splanes.domain.feature.user.usecase.InsertUserUseCase
import com.splanes.domain.feature.user.usecase.RemoveUserUseCase
import com.splanes.randomuser.MainCoroutineRule
import com.splanes.presentation.component.finder.FinderView
import com.splanes.randomuser.createDummyObserver
import com.splanes.presentation.feature.dashboard.DashboardViewModel
import com.splanes.randomuser.randomUserSet
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class DashboardFiltersTest {

    @MockK
    lateinit var getUsersUseCase: GetUsersUseCase

    @MockK
    lateinit var fetchUsersUseCase: FetchUsersUseCase

    @MockK
    lateinit var removeUserUseCase: RemoveUserUseCase

    @MockK
    lateinit var insertUserUseCase: InsertUserUseCase

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private lateinit var dashboardViewModel: DashboardViewModel

    @Before
    fun initialize() {
        MockKAnnotations.init(this)
        dashboardViewModel = DashboardViewModel(
            getUsersUseCase,
            fetchUsersUseCase,
            removeUserUseCase,
            insertUserUseCase
        )
    }

    @Test
    fun givenARandUserSet_whenNameFilterIsApplied_thenDataIsNotifiedAsDesired() {

        val userSet = randomUserSet()
        val obs = createDummyObserver<List<UserModel>>()
        dashboardViewModel.usersObservable.observeForever(obs)

        coEvery { getUsersUseCase.invoke() } returns flow { emit(Response.Success(data = userSet)) }

        // Name filter check
        dashboardViewModel.applyQuery("name-1", listOf(FinderView.QueryType.NAME))

        val slotName = slot<List<UserModel>>()
        verify { obs.onChanged(capture(slotName)) }

        Truth.assertThat(slotName.captured).hasSize(11)
        Truth.assertThat(slotName.captured.firstOrNull()?.name == "name-1").isTrue()
    }

    @Test
    fun givenARandUserSet_whenSurnameFilterIsApplied_thenDataIsNotifiedAsDesired() {

        val userSet = randomUserSet()
        val obs = createDummyObserver<List<UserModel>>()
        dashboardViewModel.usersObservable.observeForever(obs)

        coEvery { getUsersUseCase.invoke() } returns flow { emit(Response.Success(data = userSet)) }

        // Surname filter check
        dashboardViewModel.applyQuery("surname-1", listOf(FinderView.QueryType.SURNAME))

        val slotSurname = slot<List<UserModel>>()
        verify { obs.onChanged(capture(slotSurname)) }

        Truth.assertThat(slotSurname.captured).hasSize(11)
        Truth.assertThat(slotSurname.captured.firstOrNull()?.surname == "surname-1").isTrue()

    }

    @Test
    fun givenARandUserSet_whenEmailFilterIsApplied_thenDataIsNotifiedAsDesired() {

        val userSet = randomUserSet()
        val obs = createDummyObserver<List<UserModel>>()
        dashboardViewModel.usersObservable.observeForever(obs)

        coEvery { getUsersUseCase.invoke() } returns flow { emit(Response.Success(data = userSet)) }

        // Email filter check
        dashboardViewModel.applyQuery("email-1", listOf(FinderView.QueryType.EMAIL))

        val slotEmail = slot<List<UserModel>>()
        verify { obs.onChanged(capture(slotEmail)) }

        Truth.assertThat(slotEmail.captured).hasSize(11)
        Truth.assertThat(slotEmail.captured.firstOrNull()?.email == "email-1").isTrue()
    }

    @Test
    fun givenARandUserSet_whenNameAndSurnameFiltersAreApplied_thenDataIsNotifiedAsDesired() {
        val userSet = randomUserSet()
        val obs = createDummyObserver<List<UserModel>>()
        val obs2 = createDummyObserver<List<UserModel>>()
        val resultList: MutableList<List<UserModel>> = mutableListOf()
        dashboardViewModel.usersObservable.observeForever(obs)

        coEvery { getUsersUseCase.invoke() } returns flow { emit(Response.Success(data = userSet)) }

        // Name-Surname filter check

        dashboardViewModel.applyQuery(
            "name-1",
            listOf(
                FinderView.QueryType.NAME,
                FinderView.QueryType.SURNAME
            )
        )
        verify { obs.onChanged(capture(resultList)) }

        dashboardViewModel.usersObservable.removeObserver(obs)
        dashboardViewModel.usersObservable.observeForever(obs2)

        dashboardViewModel.applyQuery(
            "surname-1",
            listOf(
                FinderView.QueryType.NAME,
                FinderView.QueryType.SURNAME
            )
        )

        verify { obs2.onChanged(capture(resultList)) }

        Truth.assertThat(resultList[0]).hasSize(11)
        Truth.assertThat(resultList[1]).hasSize(11)
    }

    @Test
    fun givenARandUserSet_whenNameAndEmailFiltersAreApplied_thenDataIsNotifiedAsDesired() {

        val userSet = randomUserSet()
        val obs = createDummyObserver<List<UserModel>>()
        val obs2 = createDummyObserver<List<UserModel>>()
        val resultList: MutableList<List<UserModel>> = mutableListOf()
        dashboardViewModel.usersObservable.observeForever(obs)

        coEvery { getUsersUseCase.invoke() } returns flow { emit(Response.Success(data = userSet)) }

        // Name-Email filter check
        dashboardViewModel.applyQuery(
            "name-1",
            listOf(
                FinderView.QueryType.NAME,
                FinderView.QueryType.EMAIL
            )
        )

        verify { obs.onChanged(capture(resultList)) }

        dashboardViewModel.usersObservable.removeObserver(obs)
        dashboardViewModel.usersObservable.observeForever(obs2)

        dashboardViewModel.applyQuery(
            "email-1",
            listOf(
                FinderView.QueryType.NAME,
                FinderView.QueryType.EMAIL
            )
        )

        verify { obs2.onChanged(capture(resultList)) }

        Truth.assertThat(resultList[0]).hasSize(11)
        Truth.assertThat(resultList[1]).hasSize(11)
    }

    @Test
    fun givenARandUserSet_whenEmailAndSurnameFiltersAreApplied_thenDataIsNotifiedAsDesired() {

        val userSet = randomUserSet()
        val obs = createDummyObserver<List<UserModel>>()
        val obs2 = createDummyObserver<List<UserModel>>()
        val resultList: MutableList<List<UserModel>> = mutableListOf()
        dashboardViewModel.usersObservable.observeForever(obs)

        coEvery { getUsersUseCase.invoke() } returns flow { emit(Response.Success(data = userSet)) }

        // Email-Surname filter check
        dashboardViewModel.applyQuery(
            "email-1",
            listOf(
                FinderView.QueryType.EMAIL,
                FinderView.QueryType.SURNAME
            )
        )

        verify { obs.onChanged(capture(resultList)) }

        dashboardViewModel.usersObservable.removeObserver(obs)
        dashboardViewModel.usersObservable.observeForever(obs2)

        dashboardViewModel.applyQuery(
            "surname-1",
            listOf(
                FinderView.QueryType.EMAIL,
                FinderView.QueryType.SURNAME
            )
        )

        verify { obs2.onChanged(capture(resultList)) }

        Truth.assertThat(resultList[0]).hasSize(11)
        Truth.assertThat(resultList[1]).hasSize(11)
    }

    @Test
    fun givenARandUserSet_whenNoFilterIsApplied_thenDataIsNotifiedAsDesired() {

        val userSet = randomUserSet()
        val obs = createDummyObserver<List<UserModel>>()
        dashboardViewModel.usersObservable.observeForever(obs)

        coEvery { getUsersUseCase.invoke() } returns flow { emit(Response.Success(data = userSet)) }

        // None-filter check
        dashboardViewModel.applyQuery("blablabla", listOf())

        val slotNoneFilters1 = slot<List<UserModel>>()
        verify { obs.onChanged(capture(slotNoneFilters1)) }

        Truth.assertThat(slotNoneFilters1.captured).hasSize(40)
    }

    @Test
    fun givenARandUserSet_whenNoQueryIsApplied_thenDataIsNotifiedAsDesired() {

        val userSet = randomUserSet()
        val obs = createDummyObserver<List<UserModel>>()
        dashboardViewModel.usersObservable.observeForever(obs)

        coEvery { getUsersUseCase.invoke() } returns flow { emit(Response.Success(data = userSet)) }

        // None-query check
        dashboardViewModel.applyQuery(
            "",
            listOf(
                FinderView.QueryType.NAME,
                FinderView.QueryType.SURNAME,
                FinderView.QueryType.EMAIL
            )
        )

        val slotNoneFilters2 = slot<List<UserModel>>()
        verify { obs.onChanged(capture(slotNoneFilters2)) }

        Truth.assertThat(slotNoneFilters2.captured).hasSize(40)
    }
}
