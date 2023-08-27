package com.san.englishbender.data.local.dataSources

import database.RecordLabelCrossRef

interface IRecordLabelDataStore {

//    suspend fun getRecordLabelsFlow(recordId: String): Flow<Result<List<RecordLabelCrossRef>>>
    suspend fun insert(recordLabelCrossRef: RecordLabelCrossRef)

    suspend fun deleteByRecordId(recordId: String)
    suspend fun deleteByLabelId(labelId: String)
    suspend fun deleteByRecordLabelId(recordId: String, labelId: String)
}