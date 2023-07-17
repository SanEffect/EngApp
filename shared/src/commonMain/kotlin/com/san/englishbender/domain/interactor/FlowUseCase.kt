package com.san.englishbender.domain.interactor

import kotlinx.coroutines.flow.flowOn
import com.san.englishbender.data.Result
import com.san.englishbender.dispatcherIO
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow

abstract class FlowUseCase<out Type, in Params> {

//    protected abstract suspend fun execute(params: Params): Flow<Result<Type>>
    protected abstract suspend fun FlowCollector<Result<Type?>>.execute(params: Params)

    suspend operator fun invoke(params: Params) = flow {
        execute(params)
    }.flowOn(dispatcherIO)
}