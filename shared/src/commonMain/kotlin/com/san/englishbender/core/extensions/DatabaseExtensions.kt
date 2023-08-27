package com.san.englishbender.core.extensions

import com.san.englishbender.data.Result
import com.san.englishbender.ioDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext


suspend fun <T> doQuery(
    dispatcher: CoroutineDispatcher = ioDispatcher,
    action: suspend () -> T
): Result<T> = withContext(dispatcher) {
    try {
        Result.Success(action.invoke())
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