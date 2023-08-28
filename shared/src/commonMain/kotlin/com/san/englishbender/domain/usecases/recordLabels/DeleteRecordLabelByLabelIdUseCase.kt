package com.san.englishbender.domain.usecases.recordLabels

import com.san.englishbender.domain.repositories.IRecordLabelRepository

class DeleteRecordLabelByLabelIdUseCase(
    private val recordLabelRepository: IRecordLabelRepository
) {
    suspend operator fun invoke(labelId: String) =
        recordLabelRepository.deleteByLabelId(labelId)
}