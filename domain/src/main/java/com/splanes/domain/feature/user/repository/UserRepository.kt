package com.splanes.domain.feature.user.repository

import com.splanes.domain.common.net.Response
import com.splanes.domain.feature.user.model.UserModel

interface UserRepository {

    suspend fun fetchUsers(num: Int): Response<List<UserModel>>

}