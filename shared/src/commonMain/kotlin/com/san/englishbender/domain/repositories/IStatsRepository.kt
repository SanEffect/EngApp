package com.san.englishbender.domain.repositories

import com.san.englishbender.domain.entities.StatsEntity
import kotlinx.coroutines.flow.Flow

interface IStatsRepository {
    fun getStatsFlow(): Flow<StatsEntity?>
    suspend fun getStats(): StatsEntity?

    suspend fun updateStats(stats: StatsEntity)

    suspend fun deleteStats()
}