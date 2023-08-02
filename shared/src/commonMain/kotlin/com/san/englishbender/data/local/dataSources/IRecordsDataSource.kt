package com.san.englishbender.data.local.dataSources

import database.Record
import kotlinx.coroutines.flow.Flow

interface IRecordsDataSource {
    fun getRecordsStream(): Flow<List<Record>>
    suspend fun getRecords() : List<Record>
    suspend fun getRecordById(id: String) : Record?
    fun getRecordFlowById(id: String) : Flow<Record?>
    suspend fun insertRecord(record: Record)
    suspend fun updateRecord(record: Record)
    suspend fun deleteRecordById(id: String)
    fun getRecordsCount(): Flow<Long>
}