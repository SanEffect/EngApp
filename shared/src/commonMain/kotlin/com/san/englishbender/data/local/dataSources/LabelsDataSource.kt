package com.san.englishbender.data.local.dataSources

import com.san.englishbender.core.di.Database
import com.san.englishbender.core.extensions.getResult
import com.san.englishbender.data.Result
import com.san.englishbender.randomUUID
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import database.Label
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LabelsDataSource(db: Database) : ILabelsDataSource {

    private val queries = db.dbQueries

    override fun getAllLabels(): Flow<List<Label>> = queries.selectAllLabels().asFlow().mapToList()

    override suspend fun saveLabel(label: Label): Flow<Result<Unit>> = flow {
        emit(getResult {
            queries.insertLabel(
                id = label.id.ifEmpty { randomUUID() },
                name = label.name,
                color = label.color
            )
        })
    }

    override suspend fun deleteLabel(labelId: String): Flow<Result<Unit>> = flow {
        emit(getResult { queries.deleteLabelById(labelId) })
    }

}