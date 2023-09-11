package com.san.englishbender.domain.usecases.records

import com.san.englishbender.domain.repositories.IRecordsRepository


class GetRecordUseCase constructor(
    private val recordsRepository: IRecordsRepository
) {
    suspend operator fun invoke(id: String) =
        recordsRepository.getRecordById(id, false)
}