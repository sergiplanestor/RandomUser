package com.splanes.data.feature.user.datasource

import com.splanes.data.feature.user.api.RandomUserApi
import com.splanes.data.feature.user.response.UsersResponse
import javax.inject.Inject

class UserNetworkDataSource @Inject constructor(private val api: RandomUserApi) {

    suspend fun fetchUsers(num: Int): UsersResponse? =
        api.fetchUsers(num)

}