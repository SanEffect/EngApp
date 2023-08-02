package com.san.englishbender.domain.usecases.records

import com.san.englishbender.data.Result
import com.san.englishbender.domain.repositories.IRecordsRepository
import com.san.englishbender.domain.interactor.UseCase
import kotlinx.coroutines.flow.Flow


class RemoveRecordUseCase constructor(
    private val recordsRepository: IRecordsRepository
) : UseCase<Unit, RemoveRecordUseCase.Params> {

    data class Params(val id: String)

    override suspend fun invoke(params: Params): Flow<Result<Unit>> =
        recordsRepository.removeRecord(params.id)
}