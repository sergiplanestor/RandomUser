package com.splanes.domain.common.net

sealed class Response<out T> {
    data class Success<out T>(val data: T) : Response<T>()
    data class Failure<out T>(val throwable: Throwable?) : Response<T>()
    class Loading<out T> : Response<T>()
}
