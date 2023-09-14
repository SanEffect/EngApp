package com.san.englishbender.data.local.dataStore

import com.san.englishbender.data.local.models.AppSettings
import com.san.englishbender.data.local.models.UserSettings
import com.san.englishbender.ioDispatcher
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
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

    suspend fun Realm.write(value: RealmObject) = withContext(ioDispatcher) {
        this@write.write { copyToRealm(value) }
    }
}