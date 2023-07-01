package com.san.englishbender.domain.interactor

import com.san.englishbender.data.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

interface UseCase<out Type, in Params> {
    suspend operator fun invoke(params: Params): Flow<Result<Type>>
}