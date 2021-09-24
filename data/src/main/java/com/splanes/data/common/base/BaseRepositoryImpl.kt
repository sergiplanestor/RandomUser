package com.splanes.data.common.base

import com.splanes.domain.common.net.Response

abstract class BaseRepositoryImpl {

    protected suspend fun <T> response(
        isDataNullable: Boolean = false,
        block: suspend () -> T
    ): Response<T> =
        runCatching { block() }.mapToResponse(isDataNullable)

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
}