package com.san.englishbender.ui

import androidx.lifecycle.ViewModel
import com.san.englishbender.data.Result
import io.github.aakira.napier.log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope as androidViewModelScope


actual abstract class ViewModel actual constructor() : ViewModel() {

    actual val viewModelScope: CoroutineScope = androidViewModelScope

    protected actual val handler = CoroutineExceptionHandler { _, exception ->
        log(tag = SAFE_LAUNCH_EXCEPTION) { exception.toString() }
        handleError(exception)
    }

    protected actual fun safeLaunch(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(handler, block = block)
    }

    actual override fun onCleared() {
        super.onCleared()
    }

    actual open fun handleError(exception: Throwable) {
        log(tag = "handleError") { "handleError: $exception" }
    }

    open fun startLoading() {}

    protected actual suspend fun <T> call(
        callFlow: Flow<T>,
        completionHandler: (collect: T) -> Unit
    ) {
        callFlow
            .catch { handleError(it) }
            .collect {
                completionHandler.invoke(it)
            }
    }

    protected actual suspend fun <T> execute(
        callFlow: Flow<Result<T>>,
        completionHandler: suspend (collect: T) -> Unit
    ) {
        callFlow
            .onStart { startLoading() }
            .catch { handleError(it) }
            .collect {
                when(it) {
                    is Result.Success -> completionHandler.invoke(it.data)
                    is Result.Failure -> handleError(it.exception)
                }
            }
    }

    companion object {
        private const val SAFE_LAUNCH_EXCEPTION = "ViewModel-ExceptionHandler"
    }
}