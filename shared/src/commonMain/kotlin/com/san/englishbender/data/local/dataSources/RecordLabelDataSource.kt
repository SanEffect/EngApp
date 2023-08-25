package com.san.englishbender.data.local.dataSources

import com.san.englishbender.core.di.Database
import database.RecordLabelCrossRef

class RecordLabelDataSource(db: Database) : IRecordLabelDataStore {

    private val queries = db.dbQueries
//    override suspend fun getRecordLabelsFlow(recordId: String): Flow<Result<List<RecordLabelCrossRef>>> {
//
//        val res = queries.selectAllByRecordId(recordId).executeAsOne()
//
////        return queries.selectAllByRecordId(recordId)
////            .asFlow()
////            .mapToList()
////            .map { getResult { it } }
//    }

    override suspend fun insert(recordLabelCrossRef: RecordLabelCrossRef) {
        queries.insertRecordLabelCrossRef(
            recordId = recordLabelCrossRef.recordId,
            labelId = recordLabelCrossRef.labelId
        )
    }
}