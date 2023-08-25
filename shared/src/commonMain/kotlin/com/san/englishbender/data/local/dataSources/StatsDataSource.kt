package com.san.englishbender.data.local.dataSources

import com.san.englishbender.core.di.Database
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import database.Stats
import kotlinx.coroutines.flow.Flow

class StatsDataSource(db: Database) : IStatsDataSource {

    private val queries = db.dbQueries

    override fun getAllStats(): Flow<Stats?> = queries.selectAllStats().asFlow().mapToOneOrNull()

    override suspend fun insertStats(stats: Stats) = queries.insertStats(
        id = 1,
        recordsCount = stats.recordsCount,
        wordsCount = stats.wordsCount,
        lettersCount = stats.lettersCount
    )

    override suspend fun updateStats(stats: Stats) = queries.updateStats(
        recordsCount = stats.recordsCount,
        wordsCount = stats.wordsCount,
        lettersCount = stats.lettersCount
    )

    override suspend fun deleteStats() {
        TODO("Not yet implemented")
    }
}