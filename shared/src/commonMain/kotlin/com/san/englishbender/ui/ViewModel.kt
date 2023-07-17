package com.san.englishbender.ui

import com.san.englishbender.data.Result
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

expect abstract class ViewModel() {

    val viewModelScope: CoroutineScope

    protected val handler: CoroutineExceptionHandler

    protected fun safeLaunch(block: suspend CoroutineScope.() -> Unit)

    open fun handleError(exception: Throwable)

    protected suspend fun <T> call(
        callFlow: Flow<T>,
        completionHandler: (collect: T) -> Unit
    )

    protected suspend fun <T> execute(
        callFlow: Flow<Result<T>>,
        completionHandler: suspend (collect: T) -> Unit
    )

    protected open fun onCleared()
}