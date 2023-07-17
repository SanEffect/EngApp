package com.san.englishbender.data.local.dataSources

import com.san.englishbender.domain.entities.Record
import kotlinx.coroutines.flow.Flow

interface IRecordsDataSource {
    fun getRecordsStream(): Flow<List<Record>>
    suspend fun getRecords() : List<Record>
    suspend fun getRecordById(id: String) : Record?
    suspend fun insertRecord(record: Record)
    suspend fun deleteRecordById(id: String)
}