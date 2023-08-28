package com.san.englishbender.domain.usecases.recordLabels

import com.san.englishbender.data.Result
import com.san.englishbender.domain.repositories.IRecordLabelRepository
import database.RecordLabelCrossRef

class SaveRecordLabelUseCase(
    private val recordLabelRepository: IRecordLabelRepository
) {
    suspend operator fun invoke(recordLabel: RecordLabelCrossRef): Result<Unit> =
        recordLabelRepository.saveRecordLabel(recordLabel)
}