package com.splanes.presentation.common.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.splanes.domain.common.net.Response
import com.splanes.domain.common.net.ThrowableHandler
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope {

    private val loaderMutableObservable = MutableLiveData<Unit>()
    protected open val loaderObservable: LiveData<Unit> get() = loaderMutableObservable

    private val failureMutableObservable = MutableLiveData<Int>()
    protected open val failureObservable: LiveData<Int> get() = failureMutableObservable

    private val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    protected fun <T> launch(
        onLoading: () -> Unit = ::onLoadingDefault,
        onFailure: Throwable?.() -> Unit = ::onFailureDefault,
        onSuccess: T.() -> Unit,
        block: suspend () -> Flow<Response<T>>
    ) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { block() }.let { flow ->
                withContext(Dispatchers.Main) {
                    flow.collect {
                        handleResponse(it, onSuccess, onLoading, onFailure)
                    }
                }
            }
        }
    }

    protected open fun <T> handleResponse(
        response: Response<T>,
        onSuccess: T.() -> Unit,
        onLoading: () -> Unit,
        onFailure: Throwable?.() -> Unit
    ) {
        when (response) {
            is Response.Failure -> onFailure(response.throwable)
            is Response.Loading -> onLoading()
            is Response.Success -> onSuccess(response.data)
        }
    }

    protected open fun onLoadingDefault() {
        loaderMutableObservable.value = Unit
    }

    protected open fun onFailureDefault(throwable: Throwable?) {
        failureMutableObservable.value = ThrowableHandler.findOrDefault(throwable)
    }
}