package com.san.englishbender.domain.usecases.recordLabels

import com.san.englishbender.domain.repositories.IRecordLabelRepository
import database.RecordLabelCrossRef

class SaveRecordLabelUseCase(
    private val recordLabelRepository: IRecordLabelRepository
) {
    suspend operator fun invoke(recordLabel: RecordLabelCrossRef) =
        recordLabelRepository.saveRecordLabel(recordLabel)
}