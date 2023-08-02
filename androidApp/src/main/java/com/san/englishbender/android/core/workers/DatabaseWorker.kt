package com.san.englishbender.android.core.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.san.englishbender.core.di.Database
import com.san.englishbender.data.local.DatabaseDriverFactory
import com.san.englishbender.ioDispatcher
import com.san.englishbender.randomUUID
import database.Label
import kotlinx.coroutines.withContext


class DatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = withContext(ioDispatcher) {
        try {
            val databaseDriverFactory = DatabaseDriverFactory(applicationContext)
            val database = Database(databaseDriverFactory)

            prePopulateStats(database)
            prePopulateLabels(database)

            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private fun prePopulateStats(db: Database) {
        db.dbQueries.insertStats(
            id = 1,
            recordsCount = 0,
            wordsCount = 0,
            lettersCount = 0
        )
    }

    private fun prePopulateLabels(db: Database) {
        val labels = listOf(
            Label(
                id = randomUUID(),
                name = "Ideas",
                color = "#FF2196F3"
            ),
            Label(
                id = randomUUID(),
                name = "Thoughts",
                color = "#FF4CAF50"
            ),
            Label(
                id = randomUUID(),
                name = "Feelings",
                color = "#FFFFC107"
            ),
        )

        labels.forEach {
            db.dbQueries.insertLabel(
                id = it.id,
                name = it.name,
                color = it.color
            )
        }

    }
}