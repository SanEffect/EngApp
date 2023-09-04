package com.san.englishbender.domain.usecases.records

import com.san.englishbender.domain.entities.RecordEntity
import com.san.englishbender.domain.repositories.IRecordsRepository
import com.san.englishbender.domain.usecases.records.RecordsSort.NONE
import com.san.englishbender.domain.usecases.records.RecordsSort.ASC
import com.san.englishbender.domain.usecases.records.RecordsSort.DESC
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class GetRecordsUseCase constructor(
    private val recordsRepository: IRecordsRepository
) {
    operator fun invoke(
        forceUpdate: Boolean = false,
        sortBy: RecordsSort = NONE
    ): Flow<List<RecordEntity>> = recordsRepository.getRecordsFlow(forceUpdate)
        .map { records ->
            when (sortBy) {
                NONE -> records
                ASC -> records.sortedBy { it.creationDate }
                DESC -> records.sortedByDescending { it.creationDate }
            }
        }
}

enum class RecordsSort {
    NONE,
    ASC,
    DESC
}