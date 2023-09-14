package com.san.englishbender.data.repositories

import com.san.englishbender.core.extensions.doQuery
import com.san.englishbender.data.local.mappers.toEntity
import com.san.englishbender.data.local.mappers.toLocal
import com.san.englishbender.data.local.models.Record
import com.san.englishbender.domain.entities.RecordEntity
import com.san.englishbender.domain.repositories.IRecordsRepository
import com.san.englishbender.ioDispatcher
import com.san.englishbender.randomUUID
import io.github.aakira.napier.log
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.InitialObject
import io.realm.kotlin.notifications.InitialResults
import io.realm.kotlin.notifications.SingleQueryChange
import io.realm.kotlin.notifications.UpdatedObject
import io.realm.kotlin.notifications.UpdatedResults
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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
    }.flowOn(ioDispatcher)

    override fun getRecordFlow(id: String): Flow<RecordEntity> = flow {
        realm.query<Record>("id == $0", id).first().asFlow()
            .collect { changes: SingleQueryChange<Record> ->
                when (changes) {
                    is InitialObject<*>,
                    is UpdatedObject<*> -> changes.obj?.toEntity()?.let { emit(it) }

                    else -> {}
                }
            }
    }.flowOn(ioDispatcher)

    override suspend fun getRecords(forceUpdate: Boolean): List<RecordEntity> =
        withContext(ioDispatcher) {
            realm.query<Record>().find().map { it.toEntity() }.toList()
        }

    override suspend fun getRecordById(id: String, forceUpdate: Boolean): RecordEntity? =
        realm.query<Record>("id == $0", id).first().find()?.toEntity()

    override fun getRecordsCount(): Flow<Long?> =
        realm.query(Record::class).count().asFlow().flowOn(ioDispatcher)

    override suspend fun saveRecord(record: RecordEntity): Unit = doQuery {
        if (record.id.isEmpty()) record.id = randomUUID()
        realm.write {
            log(tag = "saveDraft") { "saveRecord write" }
            copyToRealm(record.toLocal(), UpdatePolicy.ALL)
        }
    }

    override suspend fun removeRecord(recordId: String): Unit = doQuery {
        val record = realm.query<Record>("id == $0", recordId).first()
        realm.write { delete(record) }
    }
}