package com.san.englishbender.ui.flashcards

import androidx.compose.runtime.Immutable
import com.san.englishbender.SharedRes
import com.san.englishbender.core.extensions.isNull
import com.san.englishbender.data.getResultFlow
import com.san.englishbender.data.onFailure
import com.san.englishbender.data.onSuccess
import com.san.englishbender.domain.entities.BoardEntity
import com.san.englishbender.domain.entities.FlashCardEntity
import com.san.englishbender.domain.usecases.flashCards.AddFlashCardToBoardUseCase
import com.san.englishbender.domain.usecases.flashCards.DeleteFlashCardUseCase
import com.san.englishbender.domain.usecases.flashCards.GetBoardAsFlowUseCase
import com.san.englishbender.domain.usecases.flashCards.SaveFlashCardUseCase
import com.san.englishbender.ui.ViewModel
import dev.icerock.moko.resources.StringResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update

@Immutable
data class FlashCardsUiState(
    val isLoading: Boolean = false,
    val board: BoardEntity? = null,
    val userMessage: StringResource? = null
)

class FlashCardsViewModel(
    private val getBoardAsFlowUseCase: GetBoardAsFlowUseCase,
    private val addFlashCardToBoardUseCase: AddFlashCardToBoardUseCase,
    private val saveFlashCardUseCase: SaveFlashCardUseCase,
    private val deleteFlashCardUseCase: DeleteFlashCardUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FlashCardsUiState())
    val uiState: StateFlow<FlashCardsUiState> = _uiState.asStateFlow()

    fun getBoard(boardId: String) = safeLaunch {
        getBoardAsFlowUseCase(boardId)
            .catch { showError(SharedRes.strings.remove_record_error) }
            .collect { boardEntity ->
                if (boardEntity.isNull) {
                    showError(SharedRes.strings.remove_record_error)
                    return@collect
                }
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        board = boardEntity
                    )
                }
            }
    }

    fun addCardToBoard(board: BoardEntity, flashCard: FlashCardEntity) = safeLaunch {
        getResultFlow { addFlashCardToBoardUseCase(board.id, flashCard) }
            .onFailure { showError(SharedRes.strings.remove_record_error) }
            .onSuccess {}
    }

    fun updateCard(card: FlashCardEntity) = safeLaunch {
        getResultFlow { saveFlashCardUseCase(card) }
            .onFailure {}
            .onSuccess {}
    }

    fun deleteFlashCard(cardId: String) = safeLaunch {
        getResultFlow { deleteFlashCardUseCase(cardId) }
            .onFailure { showError(SharedRes.strings.remove_record_error) }
            .onSuccess {}
    }

    private fun showError(message: StringResource) = safeLaunch {
        _uiState.update {
            it.copy(
                isLoading = false,
                userMessage = message
            )
        }
    }
}