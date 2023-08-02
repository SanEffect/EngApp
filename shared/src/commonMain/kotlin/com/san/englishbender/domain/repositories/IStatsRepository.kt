package com.san.englishbender.domain.repositories

import database.Stats
import com.san.englishbender.data.Result
import kotlinx.coroutines.flow.Flow

interface IStatsRepository {
    fun getAllStats(): Flow<Stats?>

    suspend fun insertStats(stats: Stats) : Flow<Result<Unit>>

    suspend fun updateStats(stats: Stats): Flow<Result<Unit>>

    suspend fun deleteStats(): Flow<Result<Unit>>
}