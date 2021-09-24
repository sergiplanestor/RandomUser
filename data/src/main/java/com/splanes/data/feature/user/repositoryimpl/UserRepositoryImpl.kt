package com.splanes.data.feature.user.repositoryimpl

import com.splanes.data.common.base.BaseRepositoryImpl
import com.splanes.data.feature.user.datasource.UserDataSource
import com.splanes.data.feature.user.mapper.UserMapper
import com.splanes.domain.common.net.Response
import com.splanes.domain.feature.user.model.UserModel
import com.splanes.domain.feature.user.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dataSource: UserDataSource
) : BaseRepositoryImpl(), UserRepository {

    override suspend fun fetchUsers(num: Int): Response<List<UserModel>> =
        response {
            dataSource.fetchUsers(num)?.let(UserMapper::mapToUserModel) ?: emptyList()
        }
}