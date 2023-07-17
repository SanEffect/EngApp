package com.san.englishbender.domain.usecases

import com.san.englishbender.data.Result
import com.san.englishbender.domain.entities.Record
import com.san.englishbender.domain.interactor.FlowUseCase
import com.san.englishbender.domain.repositories.IRecordsRepository
import kotlinx.coroutines.flow.FlowCollector


class GetRecordUseCase constructor(
    private val recordsRepository: IRecordsRepository
) : FlowUseCase<Record, GetRecordUseCase.Params>() {

    data class Params(val id: String)

    override suspend fun FlowCollector<Result<Record?>>.execute(params: Params) {
        emit(recordsRepository.getRecordById(params.id, false))
    }
}