package com.san.englishbender.data.repositories

import com.san.englishbender.core.extensions.doQuery
import com.san.englishbender.data.Result
import com.san.englishbender.data.local.dataSources.IStatsDataSource
import com.san.englishbender.domain.repositories.IStatsRepository
import com.san.englishbender.ioDispatcher
import database.Stats
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn


class StatsRepository(
    private val statsDataSource: IStatsDataSource
) : IStatsRepository {

    override fun getAllStats(): Flow<Stats?> =
        statsDataSource.getAllStats().flowOn(ioDispatcher)

    override suspend fun insertStats(stats: Stats): Result<Unit> = doQuery {
        statsDataSource.insertStats(stats)
    }

    override suspend fun updateStats(stats: Stats): Result<Unit> = doQuery {
        statsDataSource.updateStats(stats)
    }

    override suspend fun deleteStats(): Result<Unit> = doQuery {
        statsDataSource.deleteStats()
    }
}