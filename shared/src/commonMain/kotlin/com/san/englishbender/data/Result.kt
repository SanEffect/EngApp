package com.san.englishbender.data

import io.github.aakira.napier.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Failure(val exception: Throwable) : Result<Nothing>()

//    object Loading : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Failure -> "Failure[exception=$exception]"
        }
    }
}

/**
 * `true` if [Result] is of type [Success] & holds non-null [Success.data].
 */
val Result<*>.succeeded
    get() = this is Result.Success && data != null

fun <T> Result<T>.ifSuccess(block: (T) -> Unit) {
    if (this is Result.Success) block(data)
}

fun <T> Result<T>.ifFailure(block: (Throwable) -> Unit) {
    if (this is Result.Failure) block(exception)
}

fun <T> Result<T>.asSuccess(): T? = when (this) {
    is Result.Success -> data
    else -> null
}

fun <T> Flow<T>.asResult(): Flow<Result<T>> {
    return this
        .map<T, Result<T>> {
            Result.Success(it)
        }
//        .onStart { emit(Result.Loading) }
        .catch { emit(Result.Failure(it)) }
}

//suspend fun <T> getResult(action: suspend () -> T) = try {
//    Result.Success(action.invoke())
//} catch (e: Exception) {
//    Result.Failure(e)
//}

//suspend fun <T> getResult(action: suspend () -> Flow<T>) : T = try {
//    action.invoke().collect {
//        return@collect Result.Success(it)
//    }
////    Result.Success(action.invoke())
//} catch (e: Exception) {
//    Result.Failure(e)
//}

suspend fun <T> getResultFlow(action: suspend () -> T): Flow<Result<T>> = flow {
    return@flow try {
        emit(Result.Success(action.invoke()))
    } catch (e: Exception) {
        emit(Result.Failure(e))
    }
}

suspend fun <T> Flow<Result<T>>.onSuccess(block: suspend (T) -> Unit) {
    this.collect {
        if (it is Result.Success) block(it.data)
    }
}

fun <T> Flow<Result<T>>.onFailure(block: (Throwable) -> Unit): Flow<Result<T>> {
    return this.map {
        if (it is Result.Failure) {
            log(tag = "ifFailureException") { "ifFailure exception: ${it.exception}" }
            block(it.exception)
        }
        it
    }
}

suspend fun onFailure(action: suspend () -> Unit, block: (Throwable) -> Unit) {
    try {
        action()
    } catch (e: Exception) {
        block(e)
    }
}
