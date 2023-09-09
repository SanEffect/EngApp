package com.san.englishbender.domain.usecases.recordTags

import com.san.englishbender.data.local.models.RecordTagRef
import com.san.englishbender.domain.repositories.IRecordTagRefRepository

class SaveRecordTagRefUseCase(
    private val recordTagRefRepository: IRecordTagRefRepository
) {
    suspend operator fun invoke(recordTagRef: RecordTagRef) =
        recordTagRefRepository.saveRecordTagRef(recordTagRef)
}