package com.san.englishbender.domain.usecases.recordTags

import com.san.englishbender.domain.repositories.IRecordTagRefRepository

class DeleteRecordTagRefByTagIdUseCase(
    private val recordTagRefRepository: IRecordTagRefRepository
) {
    suspend operator fun invoke(tagId: String) =
        recordTagRefRepository.deleteByTagId(tagId)
}