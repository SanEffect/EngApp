package com.san.englishbender.core.extensions

import com.san.englishbender.data.Result
import com.san.englishbender.ioDispatcher
import io.github.aakira.napier.log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext


suspend fun <T> doQuery(
    dispatcher: CoroutineDispatcher = ioDispatcher,
    action: suspend () -> T
): T = withContext(dispatcher) {
    try {
        action.invoke()
    } catch (e: Exception) {
        logError(tag = "ExceptionHandling") { "doQuery exception: $e" }
        throw Exception(e)
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