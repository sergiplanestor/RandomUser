package com.splanes.domain.feature.user.repository

import com.splanes.domain.common.net.Response
import com.splanes.domain.feature.user.model.UserModel

interface UserRepository {

    suspend fun fetchUsers(): Response<List<UserModel>>

    suspend fun getUsers(): Response<List<UserModel>>

    suspend fun insertUser(user: UserModel): Response<Unit>

    suspend fun removeUser(user: UserModel): Response<Unit>
}