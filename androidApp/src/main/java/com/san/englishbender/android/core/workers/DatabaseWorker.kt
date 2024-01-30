package com.san.englishbender.android.core.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.san.englishbender.android.core.extensions.toHex
import com.san.englishbender.android.ui.theme.ColorsPreset
import com.san.englishbender.data.local.dataStore.IDataStore
import com.san.englishbender.data.local.models.AppSettings
import com.san.englishbender.data.local.models.Stats
import com.san.englishbender.data.local.models.Tag
import com.san.englishbender.ioDispatcher
import com.san.englishbender.randomUUID
import io.github.aakira.napier.log
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.copyFromRealm
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.ext.toRealmSet
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class DatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams), KoinComponent {

    private val realm: Realm by inject()
    private val dataStore: IDataStore by inject()

    override suspend fun doWork(): Result = withContext(ioDispatcher) {
        try {
            try {
                prePopulateStats()
                prePopulateTags()
                prePopulateColorPresets()
            } catch (e: Exception) {
                log(tag = "PrepopulateException") { "Prepopulate Exception: $e" }
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private suspend fun prePopulateStats() {
        realm.write {
            copyToRealm(
                Stats(
                    recordsCount = 0,
                    wordsCount = 0,
                    lettersCount = 0
                )
            )
        }
    }

    private suspend fun prePopulateTags() {
        val tags = listOf(
            Tag(
                id = randomUUID(),
                name = "Ideas",
                color = ColorsPreset.yellow.toHex(),
                isWhite = false
            ),
            Tag(
                id = randomUUID(),
                name = "Thoughts",
                color = ColorsPreset.green.toHex(),
                isWhite = false
            ),
            Tag(
                id = randomUUID(),
                name = "Feelings",
                color = ColorsPreset.lightBlue.toHex(),
                isWhite = false
            ),
            Tag(
                id = randomUUID(),
                name = "Creative",
                color = ColorsPreset.deepOrange.toHex(),
                isWhite = false
            )
        )

        realm.write {
            tags.forEach {
                copyToRealm(
                    Tag(
                        id = it.id,
                        name = it.name,
                        color = it.color,
                        isWhite = false
                    )
                )
            }
        }
    }

    private fun prePopulateColorPresets() {
        realm.writeBlocking {
            val appSettings = realm.query<AppSettings>().first().find()
            appSettings?.let { settings ->
                val newSettings = AppSettings().apply {
                    isFirstLaunch = settings.isFirstLaunch
                    colorPresets = ColorsPreset.values.map { it.toHex() }.toRealmList()
                }
                copyToRealm(newSettings, UpdatePolicy.ALL)
            }
        }

//        val appSettings = dataStore.getAppSettings()
//        appSettings.colorPresets = ColorsPreset.values.map { it.toHex() }.toRealmList()
//        dataStore.saveAppSettings(appSettings)
    }
}