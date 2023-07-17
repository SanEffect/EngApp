package com.san.englishbender.data.repositories

import com.san.englishbender.core.extensions.doQuery
import com.san.englishbender.core.extensions.getResult
import com.san.englishbender.core.extensions.performIfSuccess
import com.san.englishbender.data.ConcurrentHashMap
import com.san.englishbender.data.Result
import com.san.englishbender.data.Result.Failure
import com.san.englishbender.data.Result.Success
import com.san.englishbender.data.local.dataSources.IRecordsDataSource
import com.san.englishbender.data.succeeded
import com.san.englishbender.domain.entities.Record
import com.san.englishbender.domain.repositories.IRecordsRepository
import com.san.englishbender.getSystemTimeInMillis
import com.san.englishbender.ioDispatcher
import com.san.englishbender.randomUUID
import io.github.aakira.napier.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext


class RecordsRepository constructor(
    private val recordsDataSource: IRecordsDataSource
) : IRecordsRepository {

    private var cachedRecords = ConcurrentHashMap<String, Record>()

    override fun getRecordsStream(): Flow<List<Record>> = recordsDataSource.getRecordsStream()

    override val records: Flow<List<Record>> = flow {
//        val records = recordDao.getRecords().toEntities()
        emit(recordsDataSource.getRecords())
    }.flowOn(ioDispatcher)

    override suspend fun getRecordsFlow(forceUpdate: Boolean): Flow<Result<List<Record>>> =
        withContext(ioDispatcher) {
            return@withContext flow {

                log { "get from cache" }

                if (!forceUpdate && cachedRecords.isNotEmpty()) {
                    log(tag = "Caching") { "get from cache" }
                    emit(Success(cachedRecords.values.sortedByDescending { it.creationDate }))
                    return@flow
                }

                log(tag = "Caching") { "get from db" }
                val result = getResult { recordsDataSource.getRecords() }

                (result as? Success)?.let { refreshCache(it.data) }

                if (cachedRecords.isNotEmpty()) {
                    cachedRecords.values.let { records ->
                        emit(Success(records.sortedByDescending { it.creationDate }))
                        return@flow
                    }
                }
                return@flow emit(Failure(Exception("Illegal state")))
            }
        }

    override suspend fun getRecords(forceUpdate: Boolean): Result<List<Record>> {

        return withContext(ioDispatcher) {

            if (!forceUpdate) {
                cachedRecords.let { cachedRecords ->
                    return@withContext Success(
                        cachedRecords.values.sortedByDescending { it.creationDate }
                    )
                }
            }

//            val newRecords = doQuery(ioDispatcher) { recordsDao.getRecords().toEntities() }
//
//            // Refresh the cache with the new records
//            (newRecords as? Success)?.let {
//                refreshCache(it.data)
//            }

            cachedRecords.values.let { records ->
//                val record = records.first()
//
//                var recs = records.map { it.toDto() }
//
//                val success = Success(recs)

                //                return@withContext success

                //                val recordsDto = records.map { it.toDto() }.sortedByDescending { it.creationDate }
                ////                return@withContext Success(recordsDto)
                //                return@withContext Success(records.sortedByDescending { it.creationDate })
            }
            return@withContext Failure(Exception("Illegal state"))
        }
    }

    override suspend fun getRecordById(id: String, forceUpdate: Boolean): Result<Record?> {
        return getResult { recordsDataSource.getRecordById(id) }
    }

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

    //    private fun getRecordWithId(recordId: String) = cachedRecords[recordId]
    private fun getRecordWithId(recordId: String) = cachedRecords.get(recordId)

    override suspend fun refreshRecords() {
        TODO("Not yet implemented")
    }

    override suspend fun saveRecord(record: Record): Flow<Result<Unit>> = flow {
        record.id.ifEmpty {
            record.id = randomUUID()
            record.creationDate = getSystemTimeInMillis()
        }
        val result = doQuery { recordsDataSource.insertRecord(record) }
        if (result.succeeded) cacheRecord(record)
        emit(result)
    }

    override suspend fun removeRecord(recordId: String): Flow<Result<Unit>> = flow {
        performIfSuccess(doQuery { recordsDataSource.deleteRecordById(recordId) }) {
            cachedRecords.remove(recordId)
        }
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

//    override suspend fun getRecordsCount(): Flow<Result<Long>> = flow {
//        val result = doQuery(ioDispatcher) { recordDao.getRecordsCount() }
//        emit(result)
//    }
//
//    override suspend fun getWordsCount(): Flow<Result<Long>> = flow {
//        val result = doQuery(ioDispatcher) { recordDao.getWordsCount() }
//        emit(result)
//    }

    private fun refreshCache(records: List<Record>) {
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

    private fun cacheRecord(record: Record): Record {
        val cachedRecord = Record(
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