package com.san.englishbender.domain.usecases.stats

import com.san.englishbender.domain.entities.StatsEntity
import com.san.englishbender.domain.repositories.IStatsRepository
import kotlinx.coroutines.flow.Flow

class GetStatsFlowUseCase(
    private val statsRepository: IStatsRepository
) {
    operator fun invoke(): Flow<StatsEntity?> = statsRepository.getStatsFlow()
}