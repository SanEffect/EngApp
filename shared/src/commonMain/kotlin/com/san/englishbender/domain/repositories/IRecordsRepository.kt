package com.san.englishbender.domain.repositories

import com.san.englishbender.domain.entities.RecordEntity
import kotlinx.coroutines.flow.Flow

interface IRecordsRepository {

    fun getRecordsFlow(forceUpdate: Boolean): Flow<List<RecordEntity>>

    suspend fun getRecords(forceUpdate: Boolean): List<RecordEntity>

//    suspend fun getLastRecord(): Record?

    suspend fun saveRecord(record: RecordEntity): String

//    fun getRecordFlowById(id: String): Flow<RecordEntity?>
    suspend fun getRecordById(id: String, forceUpdate: Boolean): RecordEntity?

//    fun getRecordWithLabels(id: String): Flow<RecordEntity?>

    suspend fun removeRecord(recordId: String)

//    suspend fun removeRecords()

//    suspend fun deleteRecords(recordIds: List<String>)

//    suspend fun refreshRecords()

    fun getRecordsCount(): Flow<Long?>
}