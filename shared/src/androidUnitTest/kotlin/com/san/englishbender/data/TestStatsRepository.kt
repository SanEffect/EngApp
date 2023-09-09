package com.san.englishbender.data

import com.san.englishbender.data.local.models.Stats
import com.san.englishbender.domain.repositories.IStatsRepository
import kotlinx.coroutines.flow.Flow

class TestStatsRepository : IStatsRepository {
    override fun getAllStats(): Flow<Stats?> {
        TODO("Not yet implemented")
    }

    override suspend fun updateStats(stats: Stats) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteStats() {
        TODO("Not yet implemented")
    }
}