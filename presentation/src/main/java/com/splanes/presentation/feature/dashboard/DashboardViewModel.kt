package com.splanes.presentation.feature.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.splanes.domain.feature.user.model.UserModel
import com.splanes.domain.feature.user.usecase.FetchUsersUseCase
import com.splanes.domain.feature.user.usecase.GetUsersUseCase
import com.splanes.domain.feature.user.usecase.InsertUserUseCase
import com.splanes.domain.feature.user.usecase.RemoveUserUseCase
import com.splanes.presentation.common.base.BaseViewModel
import com.splanes.presentation.component.finder.FinderView
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val fetchUsersUseCase: FetchUsersUseCase,
    private val removeUserUseCase: RemoveUserUseCase,
    private val insertUserUseCase: InsertUserUseCase
) : BaseViewModel() {

    private val usersMutableObservable = MutableLiveData<List<UserModel>>()
    val usersObservable: LiveData<List<UserModel>> get() = usersMutableObservable

    private var currentQuery: String = ""
    private val currentQueryTypes: MutableList<FinderView.QueryType> = mutableListOf()

    fun getUsers() {
        launch(onSuccess = ::handleUsers) {
            getUsersUseCase.invoke()
        }
    }

    fun fetchUsers() {
        launch(onSuccess = ::handleUsers) {
            fetchUsersUseCase.invoke()
        }
    }

    fun removeUser(user: UserModel) {
        launch(onSuccess = { /* Nothing to do */ }) {
            removeUserUseCase.invoke(RemoveUserUseCase.Params(user))
        }
    }

    fun insertUser(user: UserModel) {
        launch(onSuccess = { /* Nothing to do */ }) {
            insertUserUseCase.invoke(InsertUserUseCase.Params(user))
        }
    }

    fun applyQuery(query: String, types: List<FinderView.QueryType>) {
        currentQuery = query
        currentQueryTypes.clear()
        currentQueryTypes.addAll(types)
        getUsers()
    }

    private fun handleUsers(users: List<UserModel>) {
        usersMutableObservable.value =
            if (currentQuery.isBlank()) {
                users
            } else {
                mutableListOf<UserModel>().apply {
                    currentQueryTypes.forEach { type ->
                        val subjectMapper: UserModel.() -> String = when (type) {
                            FinderView.QueryType.NAME -> { { name } }
                            FinderView.QueryType.SURNAME -> { { surname } }
                            FinderView.QueryType.EMAIL -> { { email } }
                        }
                        addAll(
                            users.filter {
                                it.subjectMapper().contains(currentQuery, ignoreCase = true)
                                        && !any { e -> it.id == e.id }
                            }
                        )
                    }
                }
            }
    }
}