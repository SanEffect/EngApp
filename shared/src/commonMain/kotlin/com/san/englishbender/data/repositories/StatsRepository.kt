package com.san.englishbender.data.repositories

import com.san.englishbender.core.extensions.getResult
import com.san.englishbender.data.Result
import com.san.englishbender.data.local.dataSources.IStatsDataSource
import com.san.englishbender.domain.repositories.IStatsRepository
import com.san.englishbender.ioDispatcher
import database.Stats
import io.github.aakira.napier.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext


class StatsRepository(
    private val statsDataSource: IStatsDataSource
) : IStatsRepository {

    override fun getAllStats(): Flow<Stats?> = statsDataSource.getAllStats()

    override suspend fun insertStats(stats: Stats): Flow<Result<Unit>> = withContext(ioDispatcher) {
        return@withContext flow {
            log { "StatsRepository.insertStats" }
            emit(getResult { statsDataSource.insertStats(stats) })
        }
    }

    override suspend fun updateStats(stats: Stats): Flow<Result<Unit>> =
        withContext(ioDispatcher) {
            return@withContext flow {
                emit(getResult { statsDataSource.updateStats(stats) })
            }
        }

    override suspend fun deleteStats(): Flow<Result<Unit>> = withContext(ioDispatcher) {
        return@withContext flow {
            emit(getResult { statsDataSource.deleteStats() })
        }
    }
}