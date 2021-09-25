package com.splanes.domain.feature.user.usecase

import com.splanes.domain.common.net.Response
import com.splanes.domain.feature.user.model.UserModel
import com.splanes.domain.feature.user.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InsertUserUseCase @Inject constructor(private val repository: UserRepository) {

    suspend operator fun invoke(params: Params): Flow<Response<Unit>> =
        flow {
            emit(Response.Loading())
            emit(repository.insertUser(params.user))
        }

    data class Params(val user: UserModel)

}

