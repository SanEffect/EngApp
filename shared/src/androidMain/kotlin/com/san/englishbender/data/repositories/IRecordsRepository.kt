package com.san.englishbender.data.repositories

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.san.englishbender.data.Result
import com.san.englishbender.domain.entities.Record
import kotlinx.coroutines.flow.Flow

interface IRecordsRepository {

    val records: Flow<List<Record>>

    //    fun getRecordsPaging(): PagingSource<Int, RecordDto>
    fun getRecordsPaging(pagingConfig: PagingConfig): Flow<PagingData<Record>>

//    fun getRecordsPaging(): Flow<DataSource.Factory<Int, Record>>
//    fun getRecordsPaging(): DataSource.Factory<Int, Record>

    suspend fun getRecordsFlow(forceUpdate: Boolean): Flow<Result<List<Record>>>

    suspend fun getRecords(forceUpdate: Boolean): Result<List<Record>>
//    suspend fun getRecords(num: Int): Result<List<Record>>

    suspend fun getLastRecord(): Result<Record?>

    suspend fun saveRecord(record: Record): Flow<Result<Unit>>

//    suspend fun getRecordById(id: String, forceUpdate: Boolean): Flow<Result<Record>>
    suspend fun getRecordById(id: String, forceUpdate: Boolean): Result<Record>

    suspend fun removeRecord(recordId: String): Flow<Result<Unit>>

    suspend fun removeRecords(): Result<Unit>

    suspend fun deleteRecords(recordIds: List<String>): Result<Unit>

    suspend fun refreshRecords()

    suspend fun getRecordsCount(): Flow<Result<Long>>

    suspend fun getWordsCount(): Flow<Result<Long>>
}