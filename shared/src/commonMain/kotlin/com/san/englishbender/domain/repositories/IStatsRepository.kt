package com.san.englishbender.domain.repositories

import com.san.englishbender.data.local.models.Stats
import kotlinx.coroutines.flow.Flow

interface IStatsRepository {
    fun getAllStats(): Flow<Stats?>

    suspend fun updateStats(stats: Stats)

    suspend fun deleteStats()
}