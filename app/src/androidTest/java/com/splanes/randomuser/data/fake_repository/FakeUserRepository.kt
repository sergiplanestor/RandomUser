package com.splanes.randomuser.data.fake_repository

import com.splanes.domain.common.date.model.DateModel
import com.splanes.domain.common.net.Response
import com.splanes.domain.feature.user.model.UserGender
import com.splanes.domain.feature.user.model.UserModel
import com.splanes.domain.feature.user.repository.UserRepository
import javax.inject.Inject
import kotlin.random.Random

class FakeUserRepository @Inject constructor(): UserRepository {

    override suspend fun fetchUsers(): Response<List<UserModel>> =
        Response.Success(data = randomUserSet())

    override suspend fun getUsers(): Response<List<UserModel>> =
        Response.Success(data = randomUserSet())

    override suspend fun insertUser(user: UserModel): Response<Unit> =
        Response.Success(data = Unit)

    override suspend fun removeUser(user: UserModel): Response<Unit> =
        Response.Success(data = Unit)

    private fun randomUserSet(size: Int = 40): List<UserModel> =
        mutableListOf<UserModel>().apply {
            for (i in 0 until size) {
                add(
                    UserModel(
                        id = "id-$i",
                        gender = if (Random.nextInt() % 2 == 0) UserGender.MALE else UserGender.FEMALE,
                        title = "title-$i",
                        name = "name-$i",
                        surname = "surname-$i",
                        address = "address-$i",
                        email = "email-$i",
                        birthday = DateModel(0, "birth-$i"),
                        registeredOn = DateModel(0, "registered-$i"),
                        phone = "phone-$i",
                        mobile = "mobile-$i",
                        thumbnailUrl = "thumbnailUrl-$i",
                        imageLargeUrl = "imageLargeUrl-$i",
                        imageUrl = "imageUrl-$i",
                        nationality = "nationality-$i"
                    )
                )
            }
        }
}