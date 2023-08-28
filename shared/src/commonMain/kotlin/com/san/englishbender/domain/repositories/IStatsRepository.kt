package com.san.englishbender.domain.repositories

import database.Stats
import com.san.englishbender.data.Result
import kotlinx.coroutines.flow.Flow

interface IStatsRepository {
    fun getAllStats(): Flow<Stats?>

    suspend fun insertStats(stats: Stats) : Result<Unit>

    suspend fun updateStats(stats: Stats): Result<Unit>

    suspend fun deleteStats(): Result<Unit>
}