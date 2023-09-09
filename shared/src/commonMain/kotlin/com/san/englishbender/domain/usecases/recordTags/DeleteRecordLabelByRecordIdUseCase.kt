package com.san.englishbender.domain.usecases.recordTags

import com.san.englishbender.domain.repositories.IRecordTagRefRepository

class DeleteRecordTagRefByRecordIdUseCase(
    private val recordTagRefRepository: IRecordTagRefRepository
) {
    suspend operator fun invoke(recordId: String) =
        recordTagRefRepository.deleteByRecordId(recordId)
}