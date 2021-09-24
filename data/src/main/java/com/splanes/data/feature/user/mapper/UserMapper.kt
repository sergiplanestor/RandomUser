package com.splanes.data.feature.user.mapper

import com.splanes.data.common.date.mapper.DateMapper
import com.splanes.data.common.date.util.orEmpty
import com.splanes.data.feature.user.response.UserLocationResponse
import com.splanes.data.feature.user.response.UserResponse
import com.splanes.data.feature.user.response.UsersResponse
import com.splanes.domain.feature.user.model.UserGender
import com.splanes.domain.feature.user.model.UserModel

object UserMapper {

    fun mapToUserModel(response: UsersResponse): List<UserModel> =
        response.results?.map(::mapToUserModel) ?: emptyList()

    private fun mapToUserModel(response: UserResponse): UserModel =
        UserModel(
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