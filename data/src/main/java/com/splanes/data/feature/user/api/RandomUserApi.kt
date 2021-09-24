package com.splanes.data.feature.user.api

import com.splanes.data.feature.user.response.UsersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserApi {

    @GET("/")
    suspend fun fetchUsers(@Query("results") num: Int) : UsersResponse?

}