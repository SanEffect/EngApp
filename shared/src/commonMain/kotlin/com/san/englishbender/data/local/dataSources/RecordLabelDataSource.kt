package com.san.englishbender.data.local.dataSources

import com.san.englishbender.core.di.Database
import com.san.englishbender.core.extensions.getResult
import com.san.englishbender.randomUUID
import database.RecordLabelCrossRef
import kotlinx.coroutines.flow.Flow
import com.san.englishbender.data.Result
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.map

class RecordLabelDataSource(db: Database) : IRecordLabelDataStore {

    private val queries = db.dbQueries
    override suspend fun getRecordLabelsFlow(recordId: String): Flow<Result<List<RecordLabelCrossRef>>> {

        val res = queries.selectAllByRecordId(recordId).executeAsOne()

//        return queries.selectAllByRecordId(recordId)
//            .asFlow()
//            .mapToList()
//            .map { getResult { it } }
    }

    override suspend fun insert(recordLabelCrossRef: RecordLabelCrossRef) {
        queries.insertRecordLabelCrossRef(
            id = randomUUID(),
            recordId = recordLabelCrossRef.recordId,
            labelId = recordLabelCrossRef.labelId
        )
    }
}