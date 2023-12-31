package com.san.englishbender.ui.stats

import com.san.englishbender.core.extensions.WhileUiSubscribed
import com.san.englishbender.domain.usecases.stats.GetStatsUseCase
import com.san.englishbender.domain.usecases.records.GetRecordsCountUseCase
import com.san.englishbender.ui.ViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn


data class StatsUiState(
    val recordsCount: Long = 0,
    val wordsCount: Long = 0,
    val lettersCount: Long = 0
)

class StatsViewModel(
    getRecordsCountUseCase: GetRecordsCountUseCase,
    getStatsUseCase: GetStatsUseCase,
) : ViewModel() {

    val uiState: StateFlow<StatsUiState> = combine(
        getRecordsCountUseCase(Unit),
        getStatsUseCase()
    ) { recordsCount, stats ->
        StatsUiState(
            recordsCount = recordsCount,
            wordsCount = stats?.wordsCount ?: 0,
            lettersCount = stats?.lettersCount ?: 0
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = WhileUiSubscribed,
            initialValue = StatsUiState()
        )
}