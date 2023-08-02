package com.san.englishbender.domain.usecases.records

import com.san.englishbender.data.Result
import com.san.englishbender.domain.interactor.UseCase
import com.san.englishbender.domain.repositories.IRecordsRepository
import kotlinx.coroutines.flow.Flow

class GetRecordsCountUseCase(
    private val recordsRepository: IRecordsRepository
) {
    operator fun invoke(params: Unit): Flow<Long> = recordsRepository.getRecordsCount()
}