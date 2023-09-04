package com.san.englishbender.domain.repositories

import database.RecordLabelCrossRef

interface IRecordLabelRepository {
    suspend fun saveRecordLabel(recordLabel: RecordLabelCrossRef)

    suspend fun deleteByRecordId(recordId: String)
    suspend fun deleteByLabelId(labelId: String)
    suspend fun deleteByRecordLabelId(recordId: String, labelId: String)
}