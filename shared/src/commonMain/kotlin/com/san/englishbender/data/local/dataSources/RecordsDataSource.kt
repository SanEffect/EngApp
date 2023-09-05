package com.san.englishbender.data.local.dataSources

import com.san.englishbender.core.di.Database
import com.san.englishbender.ioDispatcher
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import database.Record
import database.SelectRecordWithLabels
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn


class RecordsDataSource(db: Database) : IRecordsDataSource {

    private val queries = db.dbQueries

    override fun getRecordsFlow(): Flow<List<Record>> =
        queries.selectAllRecord().asFlow().mapToList().flowOn(ioDispatcher)

    override suspend fun getRecords(): List<Record> =
        queries.selectAllRecord().executeAsList()

    override suspend fun getRecordById(id: String): Record? =
        queries.selectRecordById(id).executeAsOneOrNull()

    override fun getRecordWithLabels(id: String): Flow<SelectRecordWithLabels?> =
        queries.selectRecordWithLabels(id)
            .asFlow()
            .mapToOne()
            .flowOn(ioDispatcher)

    override suspend fun insertRecord(record: Record): String {
        queries.insertRecord(
            id = record.id,
            title = record.title,
            description = record.description,
            creationDate = record.creationDate,
            isDeleted = record.isDeleted,
            isDraft = record.isDraft,
            backgroundColor = record.backgroundColor
        )
        return record.id
    }

    override suspend fun updateRecord(record: Record): String {
        queries.updateRecord(
            id = record.id,
            title = record.title,
            description = record.description,
            isDeleted = record.isDeleted,
            isDraft = record.isDraft,
            backgroundColor = record.backgroundColor
        )
        return record.id
    }

    override suspend fun deleteRecordById(id: String) = queries.deleteRecord(id)

    override fun getRecordFlowById(id: String) = queries.selectRecordById(id)
        .asFlow()
        .mapToOne()
        .flowOn(ioDispatcher)

    override fun getRecordsCount(): Flow<Long> = queries.getRecordsCount()
        .asFlow()
        .mapToOne()
        .flowOn(ioDispatcher)
}