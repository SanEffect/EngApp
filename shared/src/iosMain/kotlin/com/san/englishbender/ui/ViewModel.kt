package com.san.englishbender.ui

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel

actual abstract class ViewModel {

    actual val viewModelScope = MainScope()

    /**
     * Override this to do any cleanup immediately before the internal [CoroutineScope][kotlinx.coroutines.CoroutineScope]
     * is cancelled in [clear]
     */
    protected actual open fun onCleared() {
    }

    /**
     * Cancels the internal [CoroutineScope][kotlinx.coroutines.CoroutineScope]. After this is called, the ViewModel should
     * no longer be used.
     */
    fun clear() {
        onCleared()
        viewModelScope.cancel()
    }

    protected actual val handler: CoroutineExceptionHandler
        get() = TODO("Not yet implemented")

    protected actual fun safeLaunch(block: suspend CoroutineScope.() -> Unit) {
    }

    protected actual fun safeAsync(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.async {  }
    }

    actual open fun handleError(exception: Throwable) {
    }
}