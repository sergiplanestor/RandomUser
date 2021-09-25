package com.splanes.data.feature.user.mapper

import com.splanes.data.common.date.mapper.DateMapper
import com.splanes.data.common.date.util.orEmpty
import com.splanes.data.feature.user.database.entity.UserEntity
import com.splanes.data.feature.user.response.UserLocationResponse
import com.splanes.data.feature.user.response.UserResponse
import com.splanes.data.feature.user.response.UsersResponse
import com.splanes.domain.feature.user.model.UserGender
import com.splanes.domain.feature.user.model.UserModel

object UserMapper {

    fun mapToUserModel(response: UsersResponse): List<UserModel> =
        response.results?.map(::mapToUserModel) ?: emptyList()

    fun mapToUserModel(entity: UserEntity): UserModel =
        UserModel(
            id = entity.id,
            gender = UserGender.parse(entity.gender),
            title = entity.title,
            name = entity.name,
            surname = entity.surname,
            address = entity.address,
            email = entity.email,
            birthday = DateMapper.mapToDateModel(entity.birthday).orEmpty(),
            registeredOn = DateMapper.mapToDateModel(entity.registeredOn).orEmpty(),
            phone = entity.phone,
            mobile = entity.mobile,
            thumbnailUrl = entity.thumbnailUrl,
            imageLargeUrl = entity.imageLargeUrl,
            imageUrl = entity.imageUrl,
            nationality = entity.nationality
        )

    fun mapToUserEntity(model: UserModel): UserEntity =
        UserEntity(
            id = model.id,
            gender = model.gender.apiName,
            title = model.title,
            name = model.name,
            surname = model.surname,
            address = model.address,
            email = model.email,
            birthday = model.birthday.millis,
            registeredOn = model.registeredOn.millis,
            phone = model.phone,
            mobile = model.mobile,
            thumbnailUrl = model.thumbnailUrl,
            imageLargeUrl = model.imageLargeUrl,
            imageUrl = model.imageUrl,
            nationality = model.nationality
        )

    private fun mapToUserModel(response: UserResponse): UserModel =
        UserModel(
            id = response.id?.value.orEmpty(),
            gender = UserGender.parse(response.gender),
            title = response.name?.title.orEmpty(),
            name = response.name?.first.orEmpty(),
            surname = response.name?.last.orEmpty(),
            address = response.location?.let(::mapToAddress).orEmpty(),
            email = response.email.orEmpty(),
            birthday = DateMapper.mapToDateModel(response.birthday?.date).orEmpty(),
            registeredOn = DateMapper.mapToDateModel(response.registeredOn?.date).orEmpty(),
            phone = response.phone.orEmpty(),
            mobile = response.cell.orEmpty(),
            thumbnailUrl = response.picture?.thumbnail.orEmpty(),
            imageLargeUrl = response.picture?.large.orEmpty(),
            imageUrl = response.picture?.medium.orEmpty(),
            nationality = response.nationality.orEmpty()
        )

    private fun mapToAddress(response: UserLocationResponse): String =
        "${response.street?.build()}${if(response.street?.build() != null) ", " else ""}${response.city}\n" +
        "${response.zipCode}${if(response.zipCode != null) ", " else ""}${response.state}"
}