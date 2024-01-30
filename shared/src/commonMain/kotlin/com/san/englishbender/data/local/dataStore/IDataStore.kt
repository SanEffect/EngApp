package com.san.englishbender.data.local.dataStore

import com.san.englishbender.data.local.models.AppSettings
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent

interface IDataStore : KoinComponent {
    fun getAppSettings(): AppSettings
    fun getAppSettingsFlow(): Flow<AppSettings>

    fun saveAppSettings(appSettings: AppSettings)

    // Test methods
    fun getAppSettingsList(): List<AppSettings>
}