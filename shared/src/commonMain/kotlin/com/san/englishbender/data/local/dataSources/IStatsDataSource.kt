package com.san.englishbender.data.local.dataSources

import database.Stats
import kotlinx.coroutines.flow.Flow

interface IStatsDataSource {
    fun getAllStats() : Flow<Stats?>

    suspend fun insertStats(stats: Stats)

    suspend fun updateStats(stats: Stats)

    suspend fun deleteStats()
}