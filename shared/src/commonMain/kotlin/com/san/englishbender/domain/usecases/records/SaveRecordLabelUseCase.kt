package com.san.englishbender.domain.usecases.records

import com.san.englishbender.data.Result
import com.san.englishbender.domain.interactor.UseCase
import com.san.englishbender.domain.repositories.IRecordLabelRepository
import database.RecordLabelCrossRef
import kotlinx.coroutines.flow.Flow

class SaveRecordLabelUseCase(
    private val recordLabelRepository: IRecordLabelRepository
) : UseCase<Unit, SaveRecordLabelUseCase.Params> {

    data class Params(val recordLabel: RecordLabelCrossRef)

    override suspend fun invoke(params: Params): Flow<Result<Unit>> =
        recordLabelRepository.saveRecordLabel(params.recordLabel)
}