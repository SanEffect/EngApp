package com.san.englishbender.data.repositories

import com.san.englishbender.core.extensions.doQuery
import com.san.englishbender.data.Result
import com.san.englishbender.data.local.dataSources.IRecordLabelDataStore
import com.san.englishbender.domain.repositories.IRecordLabelRepository
import database.RecordLabelCrossRef

class RecordLabelRepository(
    private val recordLabelDataStore: IRecordLabelDataStore
) : IRecordLabelRepository {

    override suspend fun saveRecordLabel(recordLabel: RecordLabelCrossRef): Result<Unit> =
        doQuery { recordLabelDataStore.insert(recordLabel) }

    override suspend fun deleteByRecordId(recordId: String): Result<Unit> =
        doQuery { recordLabelDataStore.deleteByRecordId(recordId) }

    override suspend fun deleteByLabelId(labelId: String): Result<Unit> =
        doQuery { recordLabelDataStore.deleteByLabelId(labelId) }

    override suspend fun deleteByRecordLabelId(recordId: String, labelId: String): Result<Unit> =
        doQuery { recordLabelDataStore.deleteByRecordLabelId(recordId, labelId) }
}
