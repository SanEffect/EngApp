package com.san.englishbender.domain.usecases.recordTags

import com.san.englishbender.domain.repositories.IRecordTagRefRepository

class DeleteByRecordTagIdUseCase(
    private val recordTagRefRepository: IRecordTagRefRepository
) {
    suspend operator fun invoke(recordId: String, tagId: String) =
        recordTagRefRepository.deleteByRecordTagRefId(recordId, tagId)
}