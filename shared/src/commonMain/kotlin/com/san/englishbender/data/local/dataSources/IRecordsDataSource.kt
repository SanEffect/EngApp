package com.san.englishbender.data.local.dataSources

import database.Record
import database.SelectRecordWithLabels
import kotlinx.coroutines.flow.Flow

interface IRecordsDataSource {
    fun getRecordsFlow(): Flow<List<Record>>
    suspend fun getRecords(): List<Record>
    suspend fun getRecordById(id: String): Record?
    fun getRecordWithLabels(id: String): Flow<SelectRecordWithLabels?>
    fun getRecordFlowById(id: String): Flow<Record?>
    suspend fun insertRecord(record: Record): String
    suspend fun updateRecord(record: Record): String
    suspend fun deleteRecordById(id: String)
    fun getRecordsCount(): Flow<Long>
}