package com.san.englishbender.domain.usecases.records

import com.san.englishbender.domain.entities.RecordEntity
import com.san.englishbender.domain.repositories.IRecordsRepository
import com.san.englishbender.domain.usecases.stats.UpdateStatsUseCase
import io.github.aakira.napier.log


class RemoveRecordUseCase(
    private val recordsRepository: IRecordsRepository,
    private val updateStatsUseCase: UpdateStatsUseCase
) {
    suspend operator fun invoke(record: RecordEntity) {
        log(tag = "ExceptionHandling") { "uc removeRecord record: $record" }
        recordsRepository.removeRecord(record.id)
        updateStatsUseCase(
            isDeletion = true,
            currRecordState = record
        )
    }
}