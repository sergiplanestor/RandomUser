package com.splanes.data.feature.user.datasource

import com.splanes.data.feature.user.database.AppDatabase
import com.splanes.data.feature.user.database.dao.UserDao
import com.splanes.data.feature.user.database.entity.RemovedUserEntity
import com.splanes.data.feature.user.database.entity.UserEntity
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(private val db: AppDatabase) {

    private val dao: UserDao get() = db.userDao()

    suspend fun isAnyUserStored(): Boolean =
        dao.fetchUsers().isNotEmpty()

    suspend fun fetchUsers(): List<UserEntity> =
        dao.fetchUsers()

    suspend fun fetchRemovedUsers(): List<RemovedUserEntity> =
        dao.fetchRemovedUsers()

    suspend fun insertUsers(users: List<UserEntity>) =
        dao.insertUsers(users)

    suspend fun insertRemovedUser(id: String) =
        dao.insertRemovedUser(RemovedUserEntity(id))

    suspend fun removeUser(user: UserEntity) =
       dao.deleteUser(user)

    suspend fun removeRemovedUser(id: String) =
        dao.deleteRemovedUser(RemovedUserEntity(id))
}