package com.san.englishbender.domain.repositories

import com.san.englishbender.data.Result
import database.RecordLabelCrossRef

interface IRecordLabelRepository {
    suspend fun saveRecordLabel(recordLabel: RecordLabelCrossRef): Result<Unit>

    suspend fun deleteByRecordId(recordId: String): Result<Unit>
    suspend fun deleteByLabelId(labelId: String): Result<Unit>
    suspend fun deleteByRecordLabelId(recordId: String, labelId: String): Result<Unit>
}