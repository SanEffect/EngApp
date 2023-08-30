package com.san.englishbender.data

import com.san.englishbender.domain.repositories.IStatsRepository
import database.Stats
import kotlinx.coroutines.flow.Flow

class TestStatsRepository : IStatsRepository {
    override fun getAllStats(): Flow<Stats?> {
        TODO("Not yet implemented")
    }

    override suspend fun insertStats(stats: Stats): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun updateStats(stats: Stats): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteStats(): Result<Unit> {
        TODO("Not yet implemented")
    }
}