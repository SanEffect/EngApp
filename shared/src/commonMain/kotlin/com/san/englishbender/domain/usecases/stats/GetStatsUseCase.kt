package com.san.englishbender.domain.usecases.stats

import com.san.englishbender.domain.repositories.IStatsRepository
import database.Stats
import kotlinx.coroutines.flow.Flow

class GetStatsUseCase(
    private val statsRepository: IStatsRepository
) {
    operator fun invoke(): Flow<Stats?> = statsRepository.getAllStats()
}