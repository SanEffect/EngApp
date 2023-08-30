package com.san.englishbender.domain.repositories

import com.san.englishbender.data.Result
import com.san.englishbender.domain.entities.RecordEntity
import kotlinx.coroutines.flow.Flow

interface IRecordsRepository {

    val records: Flow<List<RecordEntity>>

    fun getRecordsStream(): Flow<List<RecordEntity>>

    suspend fun getRecordsFlow(forceUpdate: Boolean): Flow<Result<List<RecordEntity>>>

    suspend fun getRecords(forceUpdate: Boolean): Result<List<RecordEntity>>

//    suspend fun getLastRecord(): Result<Record?>

    suspend fun saveRecord(record: RecordEntity): String

    fun getRecordFlowById(id: String): Flow<RecordEntity?>
    suspend fun getRecordById(id: String, forceUpdate: Boolean): Result<RecordEntity?>

    fun getRecordWithLabels(id: String): Flow<RecordEntity?>

    suspend fun removeRecord(recordId: String): Result<Unit>

//    suspend fun removeRecords(): Result<Unit>
//
//    suspend fun deleteRecords(recordIds: List<String>): Result<Unit>

    suspend fun refreshRecords()

    fun getRecordsCount(): Flow<Long>
}