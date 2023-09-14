package com.san.englishbender.domain.usecases.records

import com.san.englishbender.domain.entities.RecordEntity
import com.san.englishbender.domain.repositories.IRecordsRepository


class SaveRecordUseCase constructor(
    private val recordsRepository: IRecordsRepository
) {
    suspend operator fun invoke(record: RecordEntity) =
        recordsRepository.saveRecord(record)
}