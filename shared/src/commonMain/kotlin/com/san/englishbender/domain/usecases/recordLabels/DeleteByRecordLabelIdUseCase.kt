package com.san.englishbender.domain.usecases.recordLabels

import com.san.englishbender.data.Result
import com.san.englishbender.domain.repositories.IRecordLabelRepository

class DeleteByRecordLabelIdUseCase(
    private val recordLabelRepository: IRecordLabelRepository
) {
    suspend operator fun invoke(recordId: String, labelId: String): Result<Unit> =
        recordLabelRepository.deleteByRecordLabelId(recordId, labelId)
}