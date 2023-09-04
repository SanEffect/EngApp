package com.san.englishbender.data.local.dataSources

import com.san.englishbender.core.di.Database
import com.san.englishbender.ioDispatcher
import com.san.englishbender.randomUUID
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import database.Label
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class LabelsDataSource(db: Database) : ILabelsDataSource {

    private val queries = db.dbQueries

    override fun getAllLabelsFlow(): Flow<List<Label>> =
        queries.selectAllLabels()
            .asFlow()
            .mapToList()
            .flowOn(ioDispatcher)

    override suspend fun getAllLabels(): List<Label> =
        queries.selectAllLabels().executeAsList()

    override suspend fun upsertLabel(label: Label) =
        when (label.id.isEmpty()) {
            true -> queries.insertLabel(
                id = randomUUID(),
                name = label.name,
                color = label.color
            )

            false -> queries.updateLabel(
                id = label.id,
                name = label.name,
                color = label.color
            )
        }

    override suspend fun deleteLabel(labelId: String) =
        queries.deleteLabelById(labelId)
}