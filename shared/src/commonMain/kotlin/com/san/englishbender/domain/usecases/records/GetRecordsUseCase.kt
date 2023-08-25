package com.san.englishbender.domain.usecases.records

import com.san.englishbender.domain.entities.RecordEntity
import com.san.englishbender.domain.repositories.IRecordsRepository
import kotlinx.coroutines.flow.Flow


class GetRecordsUseCase constructor(
    private val recordsRepository: IRecordsRepository
) {
    operator fun invoke(forceUpdate: Boolean = false): Flow<List<RecordEntity>> =
        recordsRepository.getRecordsStream()

}
