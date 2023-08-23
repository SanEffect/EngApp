package com.san.englishbender.domain.repositories

import com.san.englishbender.data.Result
import database.RecordLabelCrossRef
import kotlinx.coroutines.flow.Flow

interface IRecordLabelRepository {
    suspend fun saveRecordLabel(recordLabel: RecordLabelCrossRef): Flow<Result<Unit>>
}