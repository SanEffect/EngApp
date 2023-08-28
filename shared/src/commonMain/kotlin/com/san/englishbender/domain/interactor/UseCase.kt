package com.san.englishbender.domain.interactor

import com.san.englishbender.data.Result


interface UseCase<out Type, in Params> {
//    suspend operator fun invoke(params: Params): Flow<Result<Type>>
    suspend operator fun invoke(params: Params): Result<Type>
}