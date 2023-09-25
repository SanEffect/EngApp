package com.san.englishbender.domain.usecases.stats

import com.san.englishbender.domain.entities.RecordEntity
import com.san.englishbender.domain.repositories.IStatsRepository

class UpdateStatsUseCase(
    private val getStatsUseCase: GetStatsUseCase,
    private val statsRepository: IStatsRepository
) {
    private var prevWordsCount = 0
    private var prevLettersCount = 0

    suspend operator fun invoke(
        isDeletion: Boolean = false,
        prevRecordState: RecordEntity? = null,
        currRecordState: RecordEntity
    ) {
        getStatsUseCase()?.let { stats ->
            val title = currRecordState.title
            val description = currRecordState.description

            prevRecordState?.let { prevRecord ->
                prevWordsCount = getWordsCountOfStrings(prevRecord.title, prevRecord.description)
                prevLettersCount = getLettersOfStrings(prevRecord.title, prevRecord.description)
            }

            val recordInc = 1
            val multiplier = if (isDeletion) -1 else 1
            val wordsCount = getDiff(prevWordsCount, getWordsCountOfStrings(title, description))
            val lettersCount = getDiff(prevLettersCount, getLettersOfStrings(title, description))

            stats.recordsCount += (recordInc * multiplier)
            stats.wordsCount += (wordsCount * multiplier)
            stats.lettersCount += (lettersCount * multiplier)

            statsRepository.updateStats(stats)
        }
    }

    private fun getLettersOfStrings(vararg values: String): Int {
        return values.sumOf { it.replace(" ", "").length }
    }

    private fun getWordsCountOfStrings(vararg values: String): Int {
        return values.sumOf { it.split(" ").size }
    }

    private fun getDiff(currentValue: Int, newValue: Int): Int {
        return newValue - currentValue
    }
}