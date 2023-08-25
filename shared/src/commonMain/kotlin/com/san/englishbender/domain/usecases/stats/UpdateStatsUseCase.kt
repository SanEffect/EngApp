package com.san.englishbender.domain.usecases.stats

import com.san.englishbender.data.Result
import com.san.englishbender.domain.interactor.UseCase
import com.san.englishbender.domain.repositories.IStatsRepository
import database.Stats
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class UpdateStatsUseCase(
    private val getStatsUseCase: GetStatsUseCase,
    private val statsRepository: IStatsRepository
) : UseCase<Unit, UpdateStatsUseCase.Params> {

    data class Params(
        val recordsCount: Int,
        val wordsCount: Int,
        val lettersCount: Int
    )

    override suspend fun invoke(params: Params): Flow<Result<Unit>> {
        return getStatsUseCase().first()?.let {
            val newStats = Stats(
                id = 1,
                recordsCount = it.recordsCount + params.recordsCount,
                wordsCount = it.wordsCount + params.wordsCount,
                lettersCount = it.lettersCount + params.lettersCount
            )
            return statsRepository.updateStats(newStats)
        } ?: flow {
            Result.Success(
                Stats(
                    id = 1,
                    recordsCount = 0,
                    wordsCount = 0,
                    lettersCount = 0
                )
            )
        }
    }
}