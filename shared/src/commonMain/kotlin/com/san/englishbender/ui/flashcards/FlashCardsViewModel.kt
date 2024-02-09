package com.san.englishbender.ui.flashcards

import com.san.englishbender.SharedRes
import com.san.englishbender.core.extensions.WhileUiSubscribed
import com.san.englishbender.domain.entities.BoardEntity
import com.san.englishbender.domain.usecases.flashCards.GetBoardsFlowUseCase
import com.san.englishbender.ui.ViewModel
import dev.icerock.moko.resources.StringResource
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


data class FlashCardsUiState(
    val isLoading: Boolean = false,
    val boards: List<BoardEntity> = emptyList(),
    val userMessage: StringResource? = null
)

class FlashCardsViewModel(
    getBoardsFlowUseCase: GetBoardsFlowUseCase,
) : ViewModel() {

    val uiState: StateFlow<FlashCardsUiState> =
        getBoardsFlowUseCase()
            .map { FlashCardsUiState(boards = it) }
            .catch { FlashCardsUiState(userMessage = SharedRes.strings.loading_records_error) }
            .stateIn(
                scope = viewModelScope,
                started = WhileUiSubscribed,
                initialValue = FlashCardsUiState(isLoading = true)
            )

    fun saveBoard(boardName: String) = safeLaunch {

    }
}