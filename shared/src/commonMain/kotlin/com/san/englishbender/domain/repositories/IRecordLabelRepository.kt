package com.san.englishbender.domain.repositories

import com.san.englishbender.data.Result
import database.RecordLabelCrossRef

interface IRecordLabelRepository {
    suspend fun saveRecordLabel(recordLabel: RecordLabelCrossRef): Result<Unit>
}