package com.splanes.data.common.base

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.splanes.domain.common.net.Response
import com.splanes.domain.common.throwable.CustomThrowable

abstract class BaseRepositoryImpl(private val context: Context) {

    protected suspend fun <T> response(
        isDataNullable: Boolean = false,
        block: suspend () -> T
    ): Response<T> =
        runCatching {
            context.assertInternetConnection()
            block()
        }.mapToResponse(isDataNullable)

    @Suppress("UNCHECKED_CAST")
    protected open fun <T> Result<T>.mapToResponse(isDataNullable: Boolean): Response<T> =
        when {
            getOrNull() != null || (isSuccess && isDataNullable) -> {
                Response.Success(data = getOrNull() as T)
            }
            else -> {
                Response.Failure(exceptionOrNull())
            }
        }

    private val ConnectivityManager?.hasInternetCapability: Boolean
        get() =
            this?.getNetworkCapabilities(activeNetwork)?.run {
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            } == true

    private fun Context.assertInternetConnection() {
        (getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager).run {
            if (!hasInternetCapability) {
                throw CustomThrowable.NoInternetConnection
            }
        }
    }
}