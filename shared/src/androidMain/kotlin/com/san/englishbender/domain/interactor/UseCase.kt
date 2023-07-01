package com.san.englishbender.domain.interactor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import com.san.englishbender.data.Result
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow

abstract class LocalUseCase<out Type, in Params> {

//    protected abstract suspend fun execute(params: Params): Flow<Result<Type>>
    protected abstract suspend fun FlowCollector<Result<Type>>.execute(params: Params)

    suspend operator fun invoke(params: Params) = flow {
        execute(params)
    }.flowOn(Dispatchers.IO)
}