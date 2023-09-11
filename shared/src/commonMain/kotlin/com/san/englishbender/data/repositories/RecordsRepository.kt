package com.san.englishbender.data.repositories

import com.san.englishbender.core.extensions.doQuery
import com.san.englishbender.data.local.mappers.toEntity
import com.san.englishbender.data.local.mappers.toLocal
import com.san.englishbender.data.local.models.Record
import com.san.englishbender.data.local.models.RecordTagRef
import com.san.englishbender.data.local.models.Stats
import com.san.englishbender.domain.entities.RecordEntity
import com.san.englishbender.domain.repositories.IRecordsRepository
import com.san.englishbender.ioDispatcher
import com.san.englishbender.randomUUID
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.asFlow
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.notifications.InitialResults
import io.realm.kotlin.notifications.SingleQueryChange
import io.realm.kotlin.notifications.UpdatedResults
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext


class RecordsRepository constructor(
    private val realm: Realm,
) : IRecordsRepository {

//    private var cachedRecords = ConcurrentHashMap<String, RecordEntity>()

    override fun getRecordsFlow(forceUpdate: Boolean): Flow<List<RecordEntity>> = flow {
        realm.query<Record>().find().asFlow().collect { changes ->
            when (changes) {
                is InitialResults,
                is UpdatedResults -> emit(changes.list.toList().toEntity())

                else -> {}
            }
        }


//        return flow { realm.query<Record>().find().toList().map { it.toEntity() } }

//        return recordsDataSource.getRecordsFlow().map { it.toEntity() }

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

    override fun getRecordFlow(id: String): Flow<RecordEntity> = flow {
        realm.query<Record>("id == $0", id).first().asFlow()
            .collect { changes: SingleQueryChange<Record> ->
                when (changes) {
                    is InitialResults<*>,
                    is UpdatedResults<*> -> changes.obj?.toEntity()?.let { emit(it) }
                    else -> {}
                }
            }
    }

    override suspend fun getRecords(forceUpdate: Boolean): List<RecordEntity> =
        withContext(ioDispatcher) {

            return@withContext realm.query<Record>().find().map { it.toEntity() }.toList()

//            if (!forceUpdate) {
//                cachedRecords.let { cachedRecords ->
//                    return@withContext cachedRecords.values.sortedByDescending { it.creationDate }
//                }
//            }
//
//            val newRecords = doQuery { recordsDataSource.getRecords().toEntity() }
//            refreshCache(newRecords)
//
//            return@withContext cachedRecords.values.toList()
        }

    override suspend fun getRecordById(id: String, forceUpdate: Boolean): RecordEntity? =
        realm.query<Record>("id == $0", id).first().find()?.toEntity()

    override fun getRecordWithTags(id: String): RecordEntity {
        TODO("Not yet implemented")
    }

//    override fun getRecordWithLabels(id: String): Flow<RecordEntity?> =
//        recordsDataSource.getRecordWithLabels(id)
//            .map { it?.toEntity() }
//            .flowOn(ioDispatcher)

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

    override fun getRecordsCount(): Flow<Long?> = realm.query(Record::class).count().asFlow()

//    override fun getRecordsCount(): Flow<Long?> = flow {
//
//        realm.query(Record::class).count().asFlow().collect { changes: SingleQueryChange<Long> ->
//            when (changes) {
//                is InitialResults<*> -> emit(0L)
////                is UpdatedResults<*> -> {
////                    val res = changes.obj
////                    emit(changes.obj)
////                }
////                else -> {}
//            }
//        }
//    }.flowOn(ioDispatcher)


    override suspend fun saveRecord(record: RecordEntity): String {
//        realm.write {
//            record.id = randomUUID()
//            copyToRealm(record.toLocal())
//        }
//        return record.id

        return when (record.id.isEmpty()) {
            true -> {
                val uuid = randomUUID()
                realm.write {
                    record.id = uuid
                    copyToRealm(record.toLocal())
                }
                uuid
            }
            false -> {
                realm.write {
                    this.copyToRealm(record.toLocal(), updatePolicy = UpdatePolicy.ALL)

//                    realm.query<Record>("id == $0", record.id).first().find()?.let { rec ->
//                        rec.title = record.title
//                        rec.description = record.description
//                        rec.creationDate = record.creationDate
//                        rec.isDraft = record.isDraft
//                        rec.isDeleted = record.isDeleted
//                        rec.backgroundColor = record.backgroundColor
////                        rec.tags = record.tags?.map {
////                            RecordTagRef(recordId = record.id, tagId = it)
////                        }?.toRealmList() ?: realmListOf()
//                    }
                }
                record.id
            }
        }

//        return if (record.id.isEmpty()) {
//            doQuery {
//                record.id = randomUUID()
//                record.creationDate = getSystemTimeInMillis()
//                recordsDataSource.insertRecord(record.toLocal())
//            }
//            cacheRecord(record)
//            record.id
//        } else {
//            doQuery { recordsDataSource.updateRecord(record.toLocal()) }
//            cacheRecord(record)
//            record.id
//        }
    }

    override suspend fun removeRecord(recordId: String): Unit = doQuery {
        val record = realm.query<Record>("id == $0", recordId).first()
        realm.write { delete(record) }
    }

//    override suspend fun deleteRecords(recordIds: List<String>): Result<Unit> {
//
////        val result = recordsLocalDataSource.deleteRecords(recordIds)
//        val result = doQuery(ioDispatcher) { recordDao.deleteRecords(recordIds) }
//        if (result is Success)
//            recordIds.forEach { recId -> cachedRecords.remove(recId) }
//
//        return result
//    }

//    private fun refreshCache(records: List<RecordEntity>) {
//        cachedRecords.apply {
//            clear()
//            records
//                .sortedBy { it.creationDate }
//                .forEach {
//                    put(it.id, it)
//                }
//        }
//    }

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

//    private fun cacheRecord(record: RecordEntity): RecordEntity {
//        val cachedRecord = RecordEntity(
//            record.title,
//            record.description,
//            record.id,
//            record.isDeleted,
//            record.isDraft,
//            record.creationDate,
//            record.backgroundColor
//        )
//        cachedRecords.put(cachedRecord.id, cachedRecord)
//        return cachedRecord
//    }

//    override val randomTagId = listOf(
//        "3c95bf1c-bf1e-4109-9f68-fc3ec4efb667",
//        "fd970f1c-0208-4d72-8d08-c9a35a247dc5",
//        "a3a7ff98-ca46-4362-b2d8-306277ca7dd1",
//        "f0dfe494-d9d9-4c65-97b2-e8bb38feaed1",
//        "b70d231e-cab1-44af-86b6-4614fe99635e",
//        "f57046ce-410e-4851-b944-479be3b22c54",
//    )
//
//    override suspend fun insertRecordsTest() = withContext(ioDispatcher) {
//
//        val refs = mutableListOf<String>()
//        val recTags = realmListOf<RecordLabelCrossRef>()
//
//        repeat(1000) { i ->
//            val recId = randomUUID()
//            val record = Record(
//                id = recId,
//                title = "Rec $i",
//                description = "Desc",
//                creationDate = 0L,
//                isDraft = false,
//                isDeleted = false,
//                backgroundColor = "",
//            )
//            recordsDataSource.insertRecord(record)
//
//            val randomCount = (0..randomTagId.size).random()
//            refs.clear()
//            recTags.clear()
//
//            repeat(randomCount) {
//                val tId = randomTagId.random()
//                if (!refs.contains(tId)) refs.add(tId)
//            }
//            refs.forEach {
//                val ref = RecordLabelCrossRef(
//                    recordId = recId,
//                    labelId = it
//                )
//                recordLabelRepository.saveRecordLabel(ref)
//            }
//        }
//    }
//
//    override suspend fun readRecordsTest(): List<Record> = withContext(ioDispatcher) {
//        val records = recordsDataSource.getRecords()
//        records.forEach {
//            log(tag = "showAllRecords") { "record: $it" }
//        }
//        log(tag = "showAllRecords") { "----------------------" }
//        return@withContext records
//    }
//
//    override suspend fun getRecordsByTagIdTest(id: String): List<Record> = withContext(ioDispatcher) {
//        val records = recordsDataSource.getRecordsByTagId(id)
//        records.forEach {
//            log(tag = "showAllRecords") { "record: $it" }
//        }
//        log(tag = "showAllRecords") { "----------------------" }
//        return@withContext records
//    }
}