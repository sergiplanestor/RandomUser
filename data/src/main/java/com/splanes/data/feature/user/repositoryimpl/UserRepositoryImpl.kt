package com.splanes.data.feature.user.repositoryimpl

import android.content.Context
import com.splanes.data.common.base.BaseRepositoryImpl
import com.splanes.data.feature.user.datasource.UserLocalDataSource
import com.splanes.data.feature.user.datasource.UserNetworkDataSource
import com.splanes.data.feature.user.mapper.UserMapper
import com.splanes.domain.common.net.Response
import com.splanes.domain.feature.user.model.UserModel
import com.splanes.domain.feature.user.repository.UserRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    @ApplicationContext context: Context,
    private val localDataSource: UserLocalDataSource,
    private val networkDataSource: UserNetworkDataSource
) : BaseRepositoryImpl(context), UserRepository {

    companion object {
        const val USERS_NUM_REQ = 40
    }

    override suspend fun fetchUsers(): Response<List<UserModel>> =
        response {
            fetchAndStoreUsers(USERS_NUM_REQ)
            localDataSource.fetchUsers().map(UserMapper::mapToUserModel)
        }

    override suspend fun getUsers(): Response<List<UserModel>> =
        response {
            if (!localDataSource.isAnyUserStored()) {
                fetchAndStoreUsers(USERS_NUM_REQ)
            }
            localDataSource.fetchUsers().map(UserMapper::mapToUserModel)
        }

    override suspend fun insertUser(user: UserModel): Response<Unit> =
        response {
            localDataSource.insertUsers(listOf(user.let(UserMapper::mapToUserEntity)))
            localDataSource.removeRemovedUser(user.id)
        }

    override suspend fun removeUser(user: UserModel): Response<Unit> =
        response {
            localDataSource.removeUser(user.let(UserMapper::mapToUserEntity))
            localDataSource.insertRemovedUser(user.id)
        }

    private suspend fun fetchAndStoreUsers(num: Int) {
        val removedUsers = localDataSource.fetchRemovedUsers()
        networkDataSource.fetchUsers(num)
            ?.let(UserMapper::mapToUserModel)
            ?.filterNot { net -> removedUsers.any { net.id == it.id } }
            ?.distinctBy { it.id }
            ?.sortedWith(compareBy { it.name })
            ?.also { localDataSource.insertUsers(it.map(UserMapper::mapToUserEntity)) }
    }
}