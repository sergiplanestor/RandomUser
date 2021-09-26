package com.splanes.randomuser

import androidx.lifecycle.Observer
import com.splanes.domain.common.date.model.DateModel
import com.splanes.domain.feature.user.model.UserGender
import com.splanes.domain.feature.user.model.UserModel
import io.mockk.spyk
import kotlin.random.Random

fun <T> createDummyObserver(): Observer<T> =
    spyk(Observer<T> {})

fun randomUserSet(size: Int = 40): List<UserModel> =
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
                    birthday = DateModel(0, "01/01/70"),
                    registeredOn = DateModel(0, "01/01/70"),
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