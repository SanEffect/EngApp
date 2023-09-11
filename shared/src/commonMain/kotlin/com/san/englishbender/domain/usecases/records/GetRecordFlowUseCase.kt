package com.san.englishbender.domain.usecases.records

import com.san.englishbender.domain.entities.RecordEntity
import com.san.englishbender.domain.repositories.IRecordsRepository
import kotlinx.coroutines.flow.Flow

class GetRecordFlowUseCase(
    private val recordsRepository: IRecordsRepository
) {
    operator fun invoke(id: String): Flow<RecordEntity?> =
        recordsRepository.getRecordFlow(id)
}