package com.splanes.domain.feature.user.usecase

import com.splanes.domain.common.net.Response
import com.splanes.domain.feature.user.model.UserModel
import com.splanes.domain.feature.user.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val repository: UserRepository) {

    companion object {
        private const val DEFAULT_USERS_NUM = 40
    }

    suspend operator fun invoke(params: Params): Flow<Response<List<UserModel>>> =
        flow {
            emit(Response.Loading())
            emit(repository.fetchUsers(params.num))
        }

    data class Params(val num: Int = DEFAULT_USERS_NUM)
}