package com.splanes.presentation.feature.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.splanes.domain.feature.user.model.UserModel
import com.splanes.domain.feature.user.usecase.FetchUsersUseCase
import com.splanes.domain.feature.user.usecase.GetUsersUseCase
import com.splanes.domain.feature.user.usecase.InsertUserUseCase
import com.splanes.domain.feature.user.usecase.RemoveUserUseCase
import com.splanes.presentation.common.base.BaseViewModel
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

    fun getUsers() {
        launch(onSuccess = usersMutableObservable::setValue) {
            getUsersUseCase.invoke()
        }
    }

    fun fetchUsers() {
        launch(onSuccess = usersMutableObservable::setValue) {
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

}