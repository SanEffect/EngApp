package com.san.englishbender.domain.usecases

import androidx.paging.*
import com.san.englishbender.data.Result
import com.san.englishbender.data.repositories.IRecordsRepository
import com.san.englishbender.domain.entities.Record
import com.san.englishbender.domain.interactor.FlowPagingUseCase
import com.san.englishbender.domain.interactor.UseCase
import kotlinx.coroutines.flow.Flow


class GetRecordsPagingUseCase constructor(
//    @get:VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    private val recordsRepository: IRecordsRepository
) : FlowPagingUseCase<GetRecordsPagingUseCase.Params, Record>() {

    data class Params(
        val forceUpdate: Boolean = false,
        val pagingConfig: PagingConfig
    )

    override fun execute(params: Params): Flow<PagingData<Record>> {
        return recordsRepository.getRecordsPaging(params.pagingConfig)
    }
}

/*class GetRecordsPagingUseCase @Inject constructor(
//    @get:VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    private val recordsRepository: IRecordsRepository
) : FlowPagingUseCase<GetRecordsPagingUseCase.Params, Record>() {

    data class Params(
        val forceUpdate: Boolean = false,
        val pagingConfig: PagingConfig
    )

    override fun execute(params: Params): Flow<PagingData<Record>> {
        return Pager(
            config = params.pagingConfig,
            pagingSourceFactory = { recordsRepository.getRecordsPaging() }
        ).flow.map { pagingData ->
            pagingData.map { it.toEntity() }
        }
    }
}*/

abstract class PagingUseCase<in Params, ReturnType> where ReturnType : Any {

    protected abstract fun execute(params: Params): Flow<DataSource.Factory<Int, ReturnType>>

    operator fun invoke(params: Params): Flow<DataSource.Factory<Int, ReturnType>> = execute(params)
}

//class GetRecordsDSUseCase @Inject constructor(
//    private val recordsRepository: IRecordsRepository
//) : PagingUseCase<GetRecordsDSUseCase.Params, Record>() {
//
//    data class Params(val forceUpdate: Boolean = false)
//
//    override fun execute(params: Params): Flow<DataSource.Factory<Int, Record>> =
//        recordsRepository.getRecordsPaging()
//}

class GetRecordsUseCase constructor(
    private val recordsRepository: IRecordsRepository
) : UseCase<List<Record>, GetRecordsUseCase.Params> {

    data class Params(val forceUpdate: Boolean = false)

//    override suspend fun invoke(params: Params): Result<List<RecordEntity>> =
//        recordsRepository.getRecords(params.forceUpdate)

    override suspend fun invoke(params: Params): Flow<Result<List<Record>>> =
        recordsRepository.getRecordsFlow(params.forceUpdate)
}
