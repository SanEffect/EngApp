package com.san.englishbender.domain.usecases

import com.san.englishbender.data.Result
import com.san.englishbender.data.repositories.IRecordsRepository
import com.san.englishbender.domain.entities.Record
import com.san.englishbender.domain.interactor.LocalUseCase
import kotlinx.coroutines.flow.FlowCollector


class GetRecordUseCase constructor(
    private val recordsRepository: IRecordsRepository
) : LocalUseCase<Record, GetRecordUseCase.Params>() {

    data class Params(val id: String)

//    override suspend fun execute(params: Params): Flow<Result<Record>> =
//        recordsRepository.getRecordById(params.id, false)

    override suspend fun FlowCollector<Result<Record>>.execute(params: Params) {
        val record = recordsRepository.getRecordById(params.id, false)
        emit(record)
    }

    //    override suspend fun invoke(params: Params): Flow<Result<Record>> =
//        recordsRepository.getRecordById(params.id, false)
}