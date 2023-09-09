package com.san.englishbender.data.local.dataStore

import com.san.englishbender.data.local.models.AppSettings
import com.san.englishbender.data.local.models.Record
import com.san.englishbender.data.local.models.RecordTagRef
import com.san.englishbender.data.local.models.Tag
import com.san.englishbender.data.local.models.UserSettings
import com.san.englishbender.domain.entities.RecordEntity
import com.san.englishbender.ioDispatcher
import com.san.englishbender.randomUUID
import io.github.aakira.napier.log
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.query.RealmSingleQuery
import io.realm.kotlin.types.RealmObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext


class DataStoreRealm(
    private val realm: Realm
) : IDataStore {

    override fun getAppSettings(): AppSettings = realm.query<AppSettings>().first().find()
        ?: AppSettings().apply { isFirstLaunch = true }

    override fun saveAppSettings(appSettings: AppSettings) {
        realm.writeBlocking {
            copyToRealm(appSettings)
        }
    }

    override fun getUserSettings(): UserSettings =
        realm.query<UserSettings>().first().find() ?: UserSettings()

    override fun getUserSettingsAsFlow(): Flow<UserSettings> = flow {
        emit(realm.query<UserSettings>().first().find() ?: UserSettings())
    }

    override fun saveUserSettings(userSettings: UserSettings) {
        realm.writeBlocking {
            copyToRealm(userSettings)
        }
    }

    // ------------------------

    val tags = listOf(
        Tag("1", "Ideas", ""),
        Tag("2", "Thoughts", ""),
        Tag("3", "Feelings", ""),
        Tag("4", "Work", ""),
        Tag("5", "Design", ""),
        Tag("6", "Games", ""),
    )

    suspend fun insertRecords() = withContext(ioDispatcher) {

        try {
            val refs = mutableListOf<String>()
            val recTags = realmListOf<RecordTagRef>()

            repeat(1000) { i ->

                val recId = randomUUID()

                val randomCount = (0..tags.size).random()
                refs.clear()
                recTags.clear()

                repeat(randomCount) {
                    val tId = tags.random().id
                    if (!refs.contains(tId)) refs.add(tId)
                }
                refs.forEach {
                    val ref = RecordTagRef().apply {
                        recordId = recId
                        tagId = it
                    }
                    recTags.add(ref)
                }

                val rec = Record().apply {
                    id = recId
                    title = "Rec $i"
                    description = "Desc"
                    tags = recTags
                }

//                    log(tag = "showAllRecords") { "rec: $rec" }

                realm.write {
                    copyToRealm(rec)
                }
            }
        } catch (e: Exception) {
            log(tag = "showAllRecords") { "e: $e" }
        }
    }

    suspend fun Realm.write(value: RealmObject) = withContext(ioDispatcher) {
        this@write.write { copyToRealm(value) }
    }

    suspend fun insertTags() = withContext(ioDispatcher) {
        tags.forEach {
            realm.write { copyToRealm(it) }
        }
    }

    suspend fun getRecords(): RealmResults<Record> = withContext(ioDispatcher) {
        return@withContext realm.query<Record>().find()
    }

    fun getRecordById(id: String): RealmResults<Record> {
        return realm.query<Record>("id = $0", id).find()
    }

    suspend fun getRecordsByTagId(id: String): RealmResults<RecordTagRef> =
        withContext(ioDispatcher) {
            return@withContext realm.query<RecordTagRef>("tagId == $0", id).find()
        }

    fun getFirstRecord(): RealmSingleQuery<Record> {
        return realm.query<Record>().first()
    }

    fun getFirstTag(): RealmSingleQuery<Tag> {
        return realm.query<Tag>().first()
    }

    fun getTags(): RealmResults<Tag> {
        return realm.query<Tag>().find()
    }

    suspend fun getRecordTagRefs(): RealmResults<RecordTagRef> =
        withContext(ioDispatcher) {
            return@withContext realm.query<RecordTagRef>().find()
        }

    fun deleteTags() {



        realm.writeBlocking {
            val tags = this.query<Tag>().find()
            delete(tags)
        }
    }

    fun deleteRecords() {
        realm.writeBlocking {
            val recs = this.query<Record>().find()
            delete(recs)
        }
    }
}