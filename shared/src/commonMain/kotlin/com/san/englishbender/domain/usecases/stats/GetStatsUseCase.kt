package com.san.englishbender.domain.usecases.stats

import com.san.englishbender.domain.entities.StatsEntity
import com.san.englishbender.domain.repositories.IStatsRepository

class GetStatsUseCase(
    private val statsRepository: IStatsRepository
) {
    suspend operator fun invoke(): StatsEntity? = statsRepository.getStats()
}