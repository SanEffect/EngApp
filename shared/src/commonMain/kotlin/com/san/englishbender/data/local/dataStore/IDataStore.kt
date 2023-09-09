package com.san.englishbender.data.local.dataStore

import com.san.englishbender.data.local.models.AppSettings
import com.san.englishbender.data.local.models.UserSettings
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent

interface IDataStore : KoinComponent {
    fun getAppSettings(): AppSettings

    fun saveAppSettings(appSettings: AppSettings)

    fun getUserSettings(): UserSettings
    fun getUserSettingsAsFlow(): Flow<UserSettings>

    fun saveUserSettings(userSettings: UserSettings)
}