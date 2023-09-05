package com.san.englishbender.data.repositories

import com.san.englishbender.core.extensions.doQuery
import com.san.englishbender.data.ConcurrentHashMap
import com.san.englishbender.data.local.dataSources.IRecordsDataSource
import com.san.englishbender.data.local.mappers.toEntity
import com.san.englishbender.data.local.mappers.toLocal
import com.san.englishbender.domain.entities.RecordEntity
import com.san.englishbender.domain.repositories.IRecordsRepository
import com.san.englishbender.getSystemTimeInMillis
import com.san.englishbender.ioDispatcher
import com.san.englishbender.randomUUID
import io.github.aakira.napier.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext


class RecordsRepository constructor(
    private val recordsDataSource: IRecordsDataSource
) : IRecordsRepository {

    private var cachedRecords = ConcurrentHashMap<String, RecordEntity>()

    override fun getRecordsFlow(forceUpdate: Boolean): Flow<List<RecordEntity>> {

        return recordsDataSource.getRecordsFlow().map { it.toEntity() }

//        if (!forceUpdate && cachedRecords.isNotEmpty()) {
//            log(tag = "GetRecordsUseCase") { "get from cache" }
//            return flow {
//                emit(cachedRecords.values.sortedByDescending { it.creationDate })
//            }
//        }
//
//        log(tag = "GetRecordsUseCase") { "get from db" }
//        return recordsDataSource.getRecordsFlow().map { records ->
//            log(tag = "GetRecordsUseCase") { "getRecordsFlow records: $records" }
//            val recordEntities = records.toEntity().sortedByDescending { it.creationDate }
//            refreshCache(recordEntities)
//            recordEntities
//        }
    }

    override suspend fun getRecords(forceUpdate: Boolean): List<RecordEntity> =
        withContext(ioDispatcher) {

            if (!forceUpdate) {
                cachedRecords.let { cachedRecords ->
                    return@withContext cachedRecords.values.sortedByDescending { it.creationDate }
                }
            }

            val newRecords = doQuery { recordsDataSource.getRecords().toEntity() }
            refreshCache(newRecords)

            return@withContext cachedRecords.values.toList()
        }

    override suspend fun getRecordById(id: String, forceUpdate: Boolean): RecordEntity? =
        recordsDataSource.getRecordById(id)?.toEntity()

    override fun getRecordWithLabels(id: String): Flow<RecordEntity?> =
        recordsDataSource.getRecordWithLabels(id)
            .map { it?.toEntity() }
            .flowOn(ioDispatcher)

    /*    override suspend fun getRecordById(id: String, forceUpdate: Boolean): Result<RecordEntity?> {
            return withContext(ioDispatcher) {
                // Respond immediately with cache if available
                if (!forceUpdate) {
                    getRecordWithId(id)?.let {
                        return@withContext Success(it)
                    }
                }

    //            val newRecord = recordsLocalDataSource.getById(id)
                val newRecord = doQuery(ioDispatcher) { recordsDao.get(id) }

                // Refresh the cache with the new records
                (newRecord as? Success)?.let { cacheRecord(it.data) }

                return@withContext newRecord
            }
        }*/

    override suspend fun refreshRecords() {
        TODO("Not yet implemented")
    }

    override fun getRecordsCount(): Flow<Long> =
        recordsDataSource.getRecordsCount().flowOn(ioDispatcher)

    override suspend fun saveRecord(record: RecordEntity): String {
        return if (record.id.isEmpty()) {
            doQuery {
                record.id = randomUUID()
                record.creationDate = getSystemTimeInMillis()
                recordsDataSource.insertRecord(record.toLocal())
            }
            cacheRecord(record)
            record.id
        } else {
            doQuery { recordsDataSource.updateRecord(record.toLocal()) }
            cacheRecord(record)
            record.id
        }
    }

    override fun getRecordFlowById(id: String): Flow<RecordEntity?> =
        recordsDataSource.getRecordFlowById(id)
            .map { it?.toEntity() }
            .flowOn(ioDispatcher)

    override suspend fun removeRecord(recordId: String): Unit = doQuery {
        recordsDataSource.deleteRecordById(recordId)
        cachedRecords.remove(recordId)
    }

//    override suspend fun removeRecords(): Result<Unit> {
//        return performIfSuccess(doQuery(ioDispatcher) { recordDao.clear() }) {
//            cachedRecords.entries.clear()
//        }
//    }

//    override suspend fun deleteRecords(recordIds: List<String>): Result<Unit> {
//
////        val result = recordsLocalDataSource.deleteRecords(recordIds)
//        val result = doQuery(ioDispatcher) { recordDao.deleteRecords(recordIds) }
//        if (result is Success)
//            recordIds.forEach { recId -> cachedRecords.remove(recId) }
//
//        return result
//    }

    private fun refreshCache(records: List<RecordEntity>) {
        cachedRecords.apply {
            clear()
            records
                .sortedBy { it.creationDate }
                .forEach {
                    put(it.id, it)
                }
        }
    }

    /*    private suspend fun <Type> cacheAndPerform(
            record: RecordDto,
            performs: suspend (RecordDto) -> Result<Type>
        ): Result<Type> {
            val cachedRecord = cacheRecord(record)

            return withContext(ioDispatcher) {
                performs(cachedRecord)
            }
        }*/

//    private inline fun cacheAndPerform(record: RecordEntity, perform: (RecordEntity) -> Unit) {
//        val cachedRecord = cacheRecord(record)
//        perform(cachedRecord)
//    }

    private fun cacheRecord(record: RecordEntity): RecordEntity {
        val cachedRecord = RecordEntity(
            record.title,
            record.description,
            record.id,
            record.isDeleted,
            record.isDraft,
            record.creationDate,
            record.backgroundColor
        )
        cachedRecords.put(cachedRecord.id, cachedRecord)
        return cachedRecord
    }
}