package com.san.englishbender.core.extensions

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import com.san.englishbender.data.Result
import com.san.englishbender.dispatcherIO


suspend fun <T> doQuery(
    dispatcher: CoroutineDispatcher = dispatcherIO,
    fn: suspend () -> T
): Result<T> = withContext(dispatcher) {
    try {
        Result.Success(fn.invoke())
    } catch (e: Exception) {
        Result.Failure(e)
    }
}

suspend fun <T> getResult(action: suspend () -> T) = try {
    Result.Success(action.invoke())
} catch (e: Exception) {
    Result.Failure(e)
}

fun <T> performIfSuccess(result: Result<T>, perform: () -> Unit): Result<T> {
    if(result is Result.Success) {
        perform()
    }
    return result
}