package com.san.englishbender.domain.usecases.records

import com.san.englishbender.data.Result
import com.san.englishbender.domain.entities.RecordEntity
import com.san.englishbender.domain.interactor.UseCase
import com.san.englishbender.domain.repositories.IRecordsRepository
import kotlinx.coroutines.flow.Flow


class SaveRecordUseCase constructor(
    private val recordsRepository: IRecordsRepository
) : UseCase<String, SaveRecordUseCase.Params> {

    data class Params(val record: RecordEntity)

    override suspend fun invoke(params: Params): Flow<Result<String>> =
        recordsRepository.saveRecord(params.record)
}