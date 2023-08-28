package com.san.englishbender.data.local.dataStore

import com.san.englishbender.data.local.dataStore.models.AppSettings
import com.san.englishbender.data.local.dataStore.models.UserSettings
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.asFlow
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow


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
}