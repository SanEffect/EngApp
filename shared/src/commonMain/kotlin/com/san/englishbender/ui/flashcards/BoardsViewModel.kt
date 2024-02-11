package com.san.englishbender.ui.flashcards

import androidx.compose.runtime.Immutable
import com.san.englishbender.SharedRes
import com.san.englishbender.core.extensions.WhileUiSubscribed
import com.san.englishbender.core.extensions.isNull
import com.san.englishbender.data.getResultFlow
import com.san.englishbender.data.onFailure
import com.san.englishbender.data.onSuccess
import com.san.englishbender.domain.entities.BoardEntity
import com.san.englishbender.domain.usecases.flashCards.GetBoardByIdUseCase
import com.san.englishbender.domain.usecases.flashCards.GetBoardsFlowUseCase
import com.san.englishbender.domain.usecases.flashCards.SaveBoardUseCase
import com.san.englishbender.randomUUID
import com.san.englishbender.ui.ViewModel
import com.san.englishbender.ui.records.RecordsUiState
import dev.icerock.moko.resources.StringResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

@Immutable
data class BoardsUiState(
    val isLoading: Boolean = false,
    val boards: List<BoardEntity> = emptyList(),
    val userMessage: StringResource? = null
)

@Immutable
data class BoardUiState(
    val isLoading: Boolean = false,
    val board: BoardEntity? = null,
    val userMessage: StringResource? = null
)

class BoardsViewModel(
    private val getBoardsFlowUseCase: GetBoardsFlowUseCase,
    private val getBoardByIdUseCase: GetBoardByIdUseCase,
    private val saveBoardUseCase: SaveBoardUseCase
) : ViewModel() {

    private val _boardUiState = MutableStateFlow(BoardUiState())
    val boardUiState: StateFlow<BoardUiState> = _boardUiState.asStateFlow()

    val boardsUiState: StateFlow<BoardsUiState> =
        getBoardsFlowUseCase()
            .map { BoardsUiState(boards = it) }
            .catch { BoardsUiState(userMessage = SharedRes.strings.loading_records_error) }
            .stateIn(
                scope = viewModelScope,
                started = WhileUiSubscribed,
                initialValue = BoardsUiState(isLoading = true)
            )

    fun getBoard(boardId: String) = safeLaunch {
        getResultFlow { getBoardByIdUseCase(boardId) }
            .onFailure {
                _boardUiState.update {
                    it.copy(
                        isLoading = false,
                        userMessage = SharedRes.strings.remove_record_error
                    )
                }
            }
            .onSuccess { boardEntity ->
                if (boardEntity.isNull) {
                    _boardUiState.update {
                        it.copy(
                            isLoading = false,
                            userMessage = SharedRes.strings.remove_record_error
                        )
                    }
                    return@onSuccess
                }

                _boardUiState.update { state ->
                    state.copy(
                        isLoading = false,
                        board = boardEntity
                    )
                }
            }
    }

    fun saveBoard(board: BoardEntity) = safeLaunch {
        getResultFlow { saveBoardUseCase(board) }
            .onFailure { RecordsUiState(userMessage = SharedRes.strings.remove_record_error) }
            .onSuccess { }
    }

    fun deleteBoard(boardId: String) = safeLaunch {

    }
}