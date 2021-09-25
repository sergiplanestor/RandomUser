package com.splanes.data.feature.user.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.splanes.data.feature.user.database.dao.UserDao
import com.splanes.data.feature.user.database.entity.RemovedUserEntity
import com.splanes.data.feature.user.database.entity.UserEntity

@Database(
    entities = [UserEntity::class, RemovedUserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}