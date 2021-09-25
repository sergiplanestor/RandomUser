package com.splanes.data.feature.user.database.dao

import androidx.room.*
import com.splanes.data.feature.user.database.entity.RemovedUserEntity
import com.splanes.data.feature.user.database.entity.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    suspend fun fetchUsers(): List<UserEntity>

    @Query("SELECT * FROM removed_users")
    suspend fun fetchRemovedUsers(): List<RemovedUserEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUsers(user: List<UserEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRemovedUser(removedUser: RemovedUserEntity)

    @Delete
    suspend fun deleteUser(user: UserEntity)

    @Delete
    suspend fun deleteRemovedUser(removedUser: RemovedUserEntity)
}