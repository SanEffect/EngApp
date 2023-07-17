package com.san.englishbender.domain.usecases

import com.san.englishbender.data.Result
import com.san.englishbender.domain.repositories.IRecordsRepository
import com.san.englishbender.domain.entities.Record
import com.san.englishbender.domain.interactor.UseCase
import kotlinx.coroutines.flow.Flow


class SaveRecordUseCase constructor(
    private val recordsRepository: IRecordsRepository
) : UseCase<Unit, SaveRecordUseCase.Params> {

    data class Params(val record: Record)

    override suspend fun invoke(params: Params): Flow<Result<Unit>> =
        recordsRepository.saveRecord(params.record)
}