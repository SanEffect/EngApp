package com.san.englishbender.domain.repositories

import database.Stats
import kotlinx.coroutines.flow.Flow

interface IStatsRepository {
    fun getAllStats(): Flow<Stats?>

    suspend fun insertStats(stats: Stats)

    suspend fun updateStats(stats: Stats)

    suspend fun deleteStats()
}