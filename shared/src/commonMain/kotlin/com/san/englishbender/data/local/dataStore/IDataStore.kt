package com.san.englishbender.data.local.dataStore

import com.san.englishbender.data.local.dataStore.models.AppSettings

interface IDataStore {

    fun getAppSettings(): AppSettings

    fun saveAppSettings(appSettings: AppSettings)
}