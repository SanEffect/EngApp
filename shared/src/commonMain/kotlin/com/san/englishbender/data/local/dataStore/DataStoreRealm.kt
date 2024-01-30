package com.san.englishbender.data.local.dataStore

import com.san.englishbender.data.local.mappers.toEntity
import com.san.englishbender.data.local.models.AppSettings
import com.san.englishbender.data.local.models.Record
import com.san.englishbender.data.local.models.Stats
import com.san.englishbender.data.local.models.Tag
import com.san.englishbender.ioDispatcher
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.asFlow
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.InitialResults
import io.realm.kotlin.notifications.SingleQueryChange
import io.realm.kotlin.notifications.UpdatedResults
import io.realm.kotlin.types.RealmObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext


class DataStoreRealm(
    private val realm: Realm
) : IDataStore {

    override fun getAppSettings(): AppSettings = realm.query<AppSettings>().first().find()
        ?: AppSettings().apply { isFirstLaunch = true }

    override fun getAppSettingsFlow(): Flow<AppSettings> = flow {
        realm.query<AppSettings>().first().asFlow().collect { changes: SingleQueryChange<AppSettings> ->
            emit(changes.obj ?: AppSettings().apply { isFirstLaunch = true })
        }
    }.flowOn(ioDispatcher)

    override fun saveAppSettings(appSettings: AppSettings) {
        realm.writeBlocking {
            copyToRealm(appSettings)
        }
    }

    suspend fun Realm.write(value: RealmObject) = withContext(ioDispatcher) {
        this@write.write { copyToRealm(value, UpdatePolicy.ALL) }
    }

    // --- Test methods

    override fun getAppSettingsList(): List<AppSettings> {
        return realm.query(AppSettings::class).find()
    }
}