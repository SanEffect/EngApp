package com.san.englishbender.ui.flashcards

import com.san.englishbender.SharedRes
import com.san.englishbender.core.extensions.isNull
import com.san.englishbender.data.getResultFlow
import com.san.englishbender.data.onFailure
import com.san.englishbender.data.onSuccess
import com.san.englishbender.domain.entities.BoardEntity
import com.san.englishbender.domain.usecases.flashCards.GetBoardByIdUseCase
import com.san.englishbender.domain.usecases.flashCards.SaveBoardUseCase
import com.san.englishbender.randomUUID
import com.san.englishbender.ui.ViewModel
import com.san.englishbender.ui.records.RecordsUiState
import dev.icerock.moko.resources.StringResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


data class FlashCardsUiState(
    val isLoading: Boolean = false,
    val board: BoardEntity? = null,
    val userMessage: StringResource? = null
)

class FlashCardsViewModel(
    private val getBoardByIdUseCase: GetBoardByIdUseCase,
    private val saveBoardUseCase: SaveBoardUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FlashCardsUiState())
    val uiState: StateFlow<FlashCardsUiState> = _uiState.asStateFlow()

    fun getBoard(boardId: String) = safeLaunch {
        getResultFlow { getBoardByIdUseCase(boardId) }
            .onFailure {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        userMessage = SharedRes.strings.remove_record_error
                    )
                }
            }
            .onSuccess { boardEntity ->
                if (boardEntity.isNull) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            userMessage = SharedRes.strings.remove_record_error
                        )
                    }
                    return@onSuccess
                }

                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        board = boardEntity
                    )
                }
            }
    }

    fun saveFlashCard(boardName: String, boardColor: String) = safeLaunch {

        val board = BoardEntity().apply {
            name = boardName
            backgroundColor = boardColor
        }

        getResultFlow { saveBoardUseCase(board) }
            .onFailure { RecordsUiState(userMessage = SharedRes.strings.remove_record_error) }
            .onSuccess { }
    }

    fun deleteFlashCard(cardId: String) = safeLaunch {

    }
}