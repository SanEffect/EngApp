package com.san.englishbender.data.local.dataStore

import com.san.englishbender.data.local.dataStore.models.AppSettings
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query


class DataStore(
    private val realm: Realm
) : IDataStore {

    override fun getAppSettings(): AppSettings = realm.query<AppSettings>().first().find()
        ?: AppSettings().apply { isFirstLaunch = true }

    override fun saveAppSettings(appSettings: AppSettings) {
        realm.writeBlocking {
            copyToRealm(appSettings)
        }
    }
}