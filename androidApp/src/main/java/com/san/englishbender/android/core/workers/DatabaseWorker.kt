package com.san.englishbender.android.core.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.san.englishbender.data.local.dataStore.IDataStore
import com.san.englishbender.data.local.models.Stats
import com.san.englishbender.data.local.models.Tag
import com.san.englishbender.ioDispatcher
import com.san.englishbender.randomUUID
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.realmListOf
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
            prePopulateStats()
            prePopulateLabels()
            prePopulateLabelColors()

            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private fun prePopulateLabelColors() {
        val userSettings = dataStore.getUserSettings()
        userSettings.tagColors = realmListOf(
            "#FF000000", "#FFFFFFFF", "#FF2196F3", "#FF4CAF50", "#FFFFC107"
        )
        dataStore.saveUserSettings(userSettings)
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

    private suspend fun prePopulateLabels() {
        val tags = listOf(
            Tag(
                id = randomUUID(),
                name = "Ideas",
                color = "#FF2196F3"
            ),
            Tag(
                id = randomUUID(),
                name = "Thoughts",
                color = "#FF4CAF50"
            ),
            Tag(
                id = randomUUID(),
                name = "Feelings",
                color = "#FFFFC107"
            ),
            Tag(
                id = randomUUID(),
                name = "Work",
                color = "#FFFFC107"
            ),
            Tag(
                id = randomUUID(),
                name = "Design",
                color = "#FFFFC107"
            ),
            Tag(
                id = randomUUID(),
                name = "Games",
                color = "#FFFFC107"
            ),
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
}