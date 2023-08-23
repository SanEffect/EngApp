package com.san.englishbender.data.repositories

import com.san.englishbender.data.Result
import com.san.englishbender.data.local.dataSources.IRecordLabelDataStore
import com.san.englishbender.domain.repositories.IRecordLabelRepository
import database.RecordLabelCrossRef
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RecordLabelRepository(
    private val recordLabelDataStore: IRecordLabelDataStore
) : IRecordLabelRepository {

    override suspend fun saveRecordLabel(recordLabel: RecordLabelCrossRef): Flow<Result<Unit>> =
        flow { recordLabelDataStore.insert(recordLabel) }
}