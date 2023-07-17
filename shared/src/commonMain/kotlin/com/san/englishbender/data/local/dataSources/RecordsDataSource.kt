package com.san.englishbender.data.local.dataSources

import com.san.englishbender.core.di.Database
import com.san.englishbender.core.extensions.getResult
import com.san.englishbender.data.Result
import com.san.englishbender.data.local.mappers.toEntity
import com.san.englishbender.database.EngAppDatabase
import com.san.englishbender.domain.entities.Record
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map


//class RecordsDataSource(db: EngAppDatabase) : IRecordsDataSource {
class RecordsDataSource(db: Database) : IRecordsDataSource {

//    private val queries = db.engAppDatabaseQueries
    private val queries = db.dbQueries

    override fun getRecordsStream(): Flow<List<Record>> =
        queries.selectAllRecord().asFlow().mapToList().map { list ->
            list.map { it.toEntity() }
        }

    suspend fun getRecordsFlow() : Flow<Result<List<Record>>> = flow {
        emit(getResult { queries.selectAllRecord().executeAsList().map { it.toEntity() } })
    }

    override suspend fun getRecords(): List<Record> =
        queries.selectAllRecord().executeAsList().map { it.toEntity() }

    override suspend fun getRecordById(id: String): Record? =
        queries.selectRecordById(id).executeAsOneOrNull()?.toEntity()

    override suspend fun insertRecord(record: Record) =
        queries.insertRecord(
            id = record.id,
            title = record.title,
            description = record.description,
            creationDate = record.creationDate,
            isDeleted = record.isDeleted,
            isDraft = record.isDraft,
            backgroundColor = record.backgroundColor
        )

    override suspend fun deleteRecordById(id: String) = queries.deleteRecord(id)
}