package com.splanes.data.feature.user.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "gender") val gender: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "surname") val surname: String,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "birthday") val birthday: Long,
    @ColumnInfo(name = "registered") val registeredOn: Long,
    @ColumnInfo(name = "phone") val phone: String,
    @ColumnInfo(name = "mobile") val mobile: String,
    @ColumnInfo(name = "thumbnail_url") val thumbnailUrl: String,
    @ColumnInfo(name = "image_large_url") val imageLargeUrl: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "nationality") val nationality: String
)
