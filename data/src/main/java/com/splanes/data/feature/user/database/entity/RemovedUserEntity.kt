package com.splanes.data.feature.user.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "removed_users")
data class RemovedUserEntity(@PrimaryKey val id: String)
