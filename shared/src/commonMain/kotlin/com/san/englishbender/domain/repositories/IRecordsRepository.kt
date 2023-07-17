package com.san.englishbender.domain.repositories

import com.san.englishbender.data.Result
import com.san.englishbender.domain.entities.Record
import kotlinx.coroutines.flow.Flow

interface IRecordsRepository {

    val records: Flow<List<Record>>

    fun getRecordsStream(): Flow<List<Record>>

    suspend fun getRecordsFlow(forceUpdate: Boolean): Flow<Result<List<Record>>>

    suspend fun getRecords(forceUpdate: Boolean): Result<List<Record>>

//    suspend fun getLastRecord(): Result<Record?>

    suspend fun saveRecord(record: Record): Flow<Result<Unit>>

    suspend fun getRecordById(id: String, forceUpdate: Boolean): Result<Record?>

    suspend fun removeRecord(recordId: String): Flow<Result<Unit>>

//    suspend fun removeRecords(): Result<Unit>
//
//    suspend fun deleteRecords(recordIds: List<String>): Result<Unit>

    suspend fun refreshRecords()

//    suspend fun getRecordsCount(): Flow<Result<Long>>
//
//    suspend fun getWordsCount(): Flow<Result<Long>>
}