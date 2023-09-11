package com.san.englishbender.domain.repositories

import com.san.englishbender.domain.entities.RecordEntity
import kotlinx.coroutines.flow.Flow

interface IRecordsRepository {

    fun getRecordsFlow(forceUpdate: Boolean): Flow<List<RecordEntity>>
    fun getRecordFlow(id: String): Flow<RecordEntity>

    suspend fun getRecords(forceUpdate: Boolean): List<RecordEntity>

    suspend fun saveRecord(record: RecordEntity): String

    suspend fun getRecordById(id: String, forceUpdate: Boolean): RecordEntity?

    fun getRecordWithTags(id: String): RecordEntity

    suspend fun removeRecord(recordId: String)

//    suspend fun removeRecords()

//    suspend fun deleteRecords(recordIds: List<String>)

    fun getRecordsCount(): Flow<Long?>
}