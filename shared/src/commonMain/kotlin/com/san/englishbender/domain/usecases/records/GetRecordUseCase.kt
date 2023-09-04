package com.san.englishbender.domain.usecases.records

import com.san.englishbender.data.Result
import com.san.englishbender.domain.entities.RecordEntity
import com.san.englishbender.domain.interactor.FlowUseCase
import com.san.englishbender.domain.repositories.IRecordsRepository
import kotlinx.coroutines.flow.FlowCollector


//class GetRecordUseCase constructor(
//    private val recordsRepository: IRecordsRepository
//) : FlowUseCase<RecordEntity, GetRecordUseCase.Params>() {
//
//    data class Params(val id: String?)
//
//    override suspend fun FlowCollector<RecordEntity?>.execute(params: Params) {
//        emit(
//            when (params.id) {
//                null -> Result.Success(RecordEntity())
//                else -> recordsRepository.getRecordById(params.id, false)
//            }
//        )
////        emit(recordsRepository.getRecordById(params.id, false))
//    }
//}