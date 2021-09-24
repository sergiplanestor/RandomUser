package com.splanes.presentation.feature.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.splanes.domain.feature.user.model.UserModel
import com.splanes.domain.feature.user.usecase.GetUsersUseCase
import com.splanes.presentation.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : BaseViewModel() {

    companion object {
        const val FETCH_USERS_INCREMENT = 40
    }

    private val usersMutableObservable = MutableLiveData<List<UserModel>>()
    val usersObservable: LiveData<List<UserModel>> get() = usersMutableObservable

    private var resultNum: Int = 0

    fun fetchUsers() {
        resultNum += FETCH_USERS_INCREMENT
        launch(onSuccess = usersMutableObservable::setValue) {
            getUsersUseCase.invoke(GetUsersUseCase.Params(resultNum))
        }
    }

}