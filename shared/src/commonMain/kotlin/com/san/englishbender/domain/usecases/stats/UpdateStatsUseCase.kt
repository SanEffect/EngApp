package com.san.englishbender.domain.usecases.stats

import com.san.englishbender.data.local.models.Stats
import com.san.englishbender.domain.entities.RecordEntity
import com.san.englishbender.domain.repositories.IStatsRepository
//import com.san.englishbender.domain.repositories.IStatsRepository
import kotlinx.coroutines.flow.first

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
        getStatsUseCase().first()?.let {
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

            val newStats = Stats(
                recordsCount = it.recordsCount + (recordInc * multiplier),
                wordsCount = it.wordsCount + (wordsCount * multiplier),
                lettersCount = it.lettersCount + (lettersCount * multiplier)
            )
            statsRepository.updateStats(newStats)
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