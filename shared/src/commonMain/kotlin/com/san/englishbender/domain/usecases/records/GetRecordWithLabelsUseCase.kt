package com.san.englishbender.domain.usecases.records

import com.san.englishbender.domain.entities.RecordEntity
import com.san.englishbender.domain.repositories.IRecordsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


//class GetRecordWithLabelsUseCase(
//    private val recordsRepository: IRecordsRepository,
////    private val getLabelsUseCase: GetLabelsUseCase,
//) {
//    operator fun invoke(id: String?): Flow<RecordEntity?> = when (id) {
//        null -> flow { emit(RecordEntity()) }
//        else -> recordsRepository.getRecordWithLabels(id)
//    }
//}

//class GetRecordWithLabels(
//    private val recordRepository: IRecordsRepository
//) : UseCase<RecordEntity?, GetRecordWithLabels.Params> {
//
//    data class Params(val recordId: String)
//
//    override fun invoke(params: Params): Flow<Result<RecordEntity?>> = flow {
//        emit(recordRepository.getRecordWithLabels(params.recordId))
//    }
//}