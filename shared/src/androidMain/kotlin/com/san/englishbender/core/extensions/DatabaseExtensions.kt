package com.san.englishbender.core.extensions

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import com.san.englishbender.data.Result
import kotlinx.coroutines.Dispatchers

//suspend fun <T> doQuery(
//    dispatcher: CoroutineDispatcher = Dispatchers.IO,
//    fn: suspend () -> T
//): Result<T> = withContext(dispatcher) {
//    try {
//        Result.Success(fn.invoke())
//    } catch (e: Exception) {
//        Result.Failure(e)
//    }
//}
//
//suspend fun <T> getResult(action: suspend () -> T) = try {
//    Result.Success(action.invoke())
//} catch (e: Exception) {
//    Result.Failure(e)
//}
//
//fun <T> performIfSuccess(result: Result<T>, perform: () -> Unit): Result<T> {
//    if(result is Result.Success) {
//        perform()
//    }
//    return result
//}