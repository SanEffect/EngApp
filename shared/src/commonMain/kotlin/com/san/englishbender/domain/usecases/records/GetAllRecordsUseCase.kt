package com.san.englishbender.domain.usecases.records

import com.san.englishbender.domain.entities.RecordEntity
import com.san.englishbender.domain.repositories.IRecordsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetRecordFlowUseCase(
    private val recordsRepository: IRecordsRepository
) {
    operator fun invoke(id: String?): Flow<RecordEntity?> = when (id) {
        null -> flow { emit(RecordEntity()) }
        else -> recordsRepository.getRecordFlowById(id)
    }
}