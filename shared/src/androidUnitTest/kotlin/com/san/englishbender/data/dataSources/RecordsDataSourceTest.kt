package com.san.englishbender.data.dataSources

import com.san.englishbender.data.local.dataSources.IRecordsDataSource
import database.Record
import database.SelectRecordWithLabels
import kotlinx.coroutines.flow.Flow

class RecordsDataSourceTest(
    initialRecords: List<Record>? = emptyList()
) : IRecordsDataSource {

    private var _records: MutableMap<String, Record>? = null

    var records: List<Record>?
        get() = _records?.values?.toList()
        set(newRecords) {
            _records = newRecords?.associateBy { it.id }?.toMutableMap()
        }

    init {
        records = initialRecords
    }

    override fun getRecordsFlow(): Flow<List<Record>> {
        TODO("Not yet implemented")
    }

    override suspend fun getRecords(): List<Record> {
        return records ?: listOf()
    }

    override suspend fun getRecordById(id: String): Record? {
        TODO("Not yet implemented")
    }

    override fun getRecordWithLabels(id: String): Flow<SelectRecordWithLabels?> {
        TODO("Not yet implemented")
    }

    override fun getRecordFlowById(id: String): Flow<Record?> {
        TODO("Not yet implemented")
    }

    override suspend fun insertRecord(record: Record): String {
        TODO("Not yet implemented")
    }

    override suspend fun updateRecord(record: Record): String {
        TODO("Not yet implemented")
    }

    override suspend fun deleteRecordById(id: String) {
        _records?.remove(id)
    }

    override fun getRecordsCount(): Flow<Long> {
        TODO("Not yet implemented")
    }
}