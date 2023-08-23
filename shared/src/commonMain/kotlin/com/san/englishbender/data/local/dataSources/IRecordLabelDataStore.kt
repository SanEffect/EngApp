package com.san.englishbender.data.local.dataSources

import database.RecordLabelCrossRef
import kotlinx.coroutines.flow.Flow
import com.san.englishbender.data.Result

interface IRecordLabelDataStore {

    suspend fun getRecordLabelsFlow(recordId: String): Flow<Result<List<RecordLabelCrossRef>>>
    suspend fun insert(recordLabelCrossRef: RecordLabelCrossRef)
}