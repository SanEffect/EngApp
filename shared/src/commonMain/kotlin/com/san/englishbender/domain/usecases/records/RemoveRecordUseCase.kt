package com.san.englishbender.domain.usecases.records

import com.san.englishbender.domain.entities.RecordEntity
import com.san.englishbender.domain.repositories.IRecordsRepository
import com.san.englishbender.domain.usecases.stats.UpdateStatsUseCase


class RemoveRecordUseCase constructor(
    private val recordsRepository: IRecordsRepository,
    private val updateStatsUseCase: UpdateStatsUseCase
) {
    suspend operator fun invoke(record: RecordEntity) {
        recordsRepository.removeRecord(record.id)
        updateStatsUseCase(
            isDeletion = true,
            currRecordState = record
        )
    }
}