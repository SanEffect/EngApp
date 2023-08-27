package com.san.englishbender.domain.usecases.recordLabels

import com.san.englishbender.domain.repositories.IRecordLabelRepository

class DeleteRecordLabelByRecordIdUseCase(
    private val recordLabelRepository: IRecordLabelRepository
) {
    suspend operator fun invoke(recordId: String) =
        recordLabelRepository.deleteByRecordId(recordId)
}