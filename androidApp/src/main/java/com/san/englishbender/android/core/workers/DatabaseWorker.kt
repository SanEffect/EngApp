package com.san.englishbender.android.core.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.san.englishbender.android.ui.theme.ColorsPreset
import com.san.englishbender.data.local.dataStore.IDataStore
import com.san.englishbender.data.local.models.Stats
import com.san.englishbender.data.local.models.Tag
import com.san.englishbender.ioDispatcher
import com.san.englishbender.randomUUID
import io.github.aakira.napier.log
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.toRealmList
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
                prePopulateTagColors()
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
                color = ColorsPreset.yellow.toString()
            ),
            Tag(
                id = randomUUID(),
                name = "Thoughts",
                color = ColorsPreset.green.toString()
            ),
            Tag(
                id = randomUUID(),
                name = "Feelings",
                color = ColorsPreset.lightBlue.toString()
            ),
            Tag(
                id = randomUUID(),
                name = "Creative",
                color = ColorsPreset.deepOrange.toString()
            )
        )

        tags.forEach {
            realm.write {
                copyToRealm(
                    Tag(
                        id = it.id,
                        name = it.name,
                        color = it.color
                    )
                )
            }
        }
    }

    private fun prePopulateTagColors() {
        val userSettings = dataStore.getUserSettings()
        userSettings.tagColors = ColorsPreset.values.map { it.toString() }.toRealmList()
        dataStore.saveUserSettings(userSettings)
    }
}