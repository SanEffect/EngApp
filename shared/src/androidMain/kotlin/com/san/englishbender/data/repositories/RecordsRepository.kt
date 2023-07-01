package com.san.englishbender.data.repositories

import androidx.paging.*
import com.san.englishbender.core.extensions.*
import com.san.englishbender.domain.entities.Record
import com.san.englishbender.data.local.dao.RecordsDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import com.san.englishbender.data.Result
import com.san.englishbender.data.Result.Success
import com.san.englishbender.data.Result.Failure
import com.san.englishbender.data.local.dto.RecordDto
import com.san.englishbender.data.local.dto.toDto
import com.san.englishbender.data.local.dto.toEntities
import com.san.englishbender.data.local.dto.toEntity
import kotlinx.coroutines.flow.*
import java.util.*


class RecordsRepository constructor(
    private val recordsDao: RecordsDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : IRecordsRepository {

    private var cachedRecords: ConcurrentMap<String, Record> = ConcurrentHashMap()
//    private var cachedRecordDtos: ConcurrentMap<String, RecordDto> = ConcurrentHashMap()

    override fun getRecordsPaging(pagingConfig: PagingConfig): Flow<PagingData<Record>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { recordsDao.getRecordsPaging() }
        ).flow.map { pagingData ->
            pagingData.map {
                it.toEntity()
            }
        }.flowOn(Dispatchers.IO)
    }

    override val records: Flow<List<Record>> = flow {
        val records = recordsDao.getRecords().toEntities()
        emit(records)
    }.flowOn(Dispatchers.IO)

    override suspend fun getRecordsFlow(forceUpdate: Boolean): Flow<Result<List<Record>>> =
        withContext(ioDispatcher) {
            return@withContext flow {

                if (!forceUpdate && cachedRecords.isNotEmpty()) {
                    Timber.tag("Caching").d("get from cache")
                    emit(Success(cachedRecords.values.sortedByDescending { it.creationDate }))
                    return@flow
                }

//                recordsDao.getRecords().map { it.toEntities() }.collect {recs ->
//                    val res = recs
//                    Timber.tag("getRecordsFlow").d("recs size: ${recs.size}")
//                    emit(Success(recs.sortedByDescending { it.creationDate }))
//                }

//                val recs = doQuery(Dispatchers.IO) { recordsDao.getRecords().toEntities() }

                Timber.tag("Caching").d("get from db")
                val result = getResult { recordsDao.getRecords().toEntities() }

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

                    return@withContext Success(cachedRecords.values.sortedByDescending { it.creationDate })
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

//    override suspend fun getRecordById(id: String, forceUpdate: Boolean): Flow<Result<Record>> =
    override suspend fun getRecordById(id: String, forceUpdate: Boolean): Result<Record> {

        return getResult { recordsDao.get(id).toEntity() }

//        val result = getResult {
//            recordsDao.get(id).toEntity()
//        }
//        emit(result)
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

    private fun getRecordWithId(recordId: String) = cachedRecords[recordId]

    override suspend fun refreshRecords() {
        TODO("Not yet implemented")
    }

    override suspend fun getLastRecord(): Result<Record> =
        doQuery(ioDispatcher) { recordsDao.getLastRecord().toEntity() }

    override suspend fun saveRecord(record: Record): Flow<Result<Unit>> = flow {
        cacheRecord(record)

        val recordDto = record.toDto()
        recordDto.id.ifEmpty {
            recordDto.id = UUID.randomUUID().toString()
            recordDto.creationDate = System.currentTimeMillis()
        }
        emit(doQuery(ioDispatcher) { recordsDao.insert(recordDto) })
    }

    override suspend fun removeRecord(recordId: String): Flow<Result<Unit>> {
        return flow {
            performIfSuccess(doQuery(ioDispatcher) { recordsDao.removeRecord(recordId) }) {
                cachedRecords.remove(recordId)
            }
        }
    }

    override suspend fun removeRecords(): Result<Unit> {
        return performIfSuccess(doQuery(ioDispatcher) { recordsDao.clear() }) {
            cachedRecords.entries.clear()
        }
    }

    override suspend fun deleteRecords(recordIds: List<String>): Result<Unit> {

//        val result = recordsLocalDataSource.deleteRecords(recordIds)
        val result = doQuery(ioDispatcher) { recordsDao.deleteRecords(recordIds) }
        if (result is Success)
            recordIds.forEach { recId -> cachedRecords.remove(recId) }

        return result
    }

    override suspend fun getRecordsCount(): Flow<Result<Long>> = flow {
        val result = doQuery(ioDispatcher) { recordsDao.getRecordsCount() }
        emit(result)
    }

    override suspend fun getWordsCount(): Flow<Result<Long>> = flow {
        val result = doQuery(ioDispatcher) { recordsDao.getWordsCount() }
        emit(result)
    }

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

/*    private fun refreshCacheDto(records: List<Record>) {
        cachedRecordDtos.apply {
            clear()
            records
                .sortedBy { it.creationDate }
                .forEach {
                    put(it.id, it)
                }
        }
    }*/

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

//    private fun cacheRecordDto(record: RecordDto): RecordDto {
//        val cachedRecordDto = RecordDto(
//            record.title,
//            record.description,
//            record.creationDate,
//            record.isDeleted,
//            record.isDraft,
//            record.backgroundColor,
//            record.id
//        )
//        cachedRecords[cachedRecordDto.id] = cachedRecordDto
//        return cachedRecordDto
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
        cachedRecords[cachedRecord.id] = cachedRecord
        return cachedRecord
    }
}