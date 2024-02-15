package com.san.englishbender.ui.flashcards

import androidx.compose.runtime.Immutable
import com.san.englishbender.SharedFileReader
import com.san.englishbender.SharedRes
import com.san.englishbender.core.extensions.WhileUiSubscribed
import com.san.englishbender.data.getResultFlow
import com.san.englishbender.data.onFailure
import com.san.englishbender.data.onSuccess
import com.san.englishbender.domain.entities.BoardEntity
import com.san.englishbender.domain.entities.FlashCardEntity
import com.san.englishbender.domain.usecases.flashCards.DeleteBoardUseCase
import com.san.englishbender.domain.usecases.flashCards.GetBoardsFlowUseCase
import com.san.englishbender.domain.usecases.flashCards.SaveBoardUseCase
import com.san.englishbender.ui.ViewModel
import dev.icerock.moko.resources.StringResource
import io.github.aakira.napier.log
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Immutable
data class BoardsUiState(
    val isLoading: Boolean = false,
    val boards: List<BoardEntity> = emptyList(),
    val userMessage: StringResource? = null
)

class BoardsViewModel(
    private val getBoardsFlowUseCase: GetBoardsFlowUseCase,
    private val saveBoardUseCase: SaveBoardUseCase,
    private val deleteBoardUseCase: DeleteBoardUseCase
) : ViewModel() {

    val uiState: StateFlow<BoardsUiState> =
        getBoardsFlowUseCase()
            .map { BoardsUiState(boards = it) }
            .catch { BoardsUiState(userMessage = SharedRes.strings.loading_records_error) }
            .stateIn(
                scope = viewModelScope,
                started = WhileUiSubscribed,
                initialValue = BoardsUiState(isLoading = true)
            )

    fun saveBoard(board: BoardEntity) = safeLaunch {
        getResultFlow { saveBoardUseCase(board) }
            .onFailure { showError(SharedRes.strings.remove_record_error) }
            .onSuccess {}
    }

    @Serializable
    data class CardsContainer(
        val cards: List<FlashCardEntity> = emptyList()
    )

    fun json() = safeLaunch {
        val listOfCards = CardsContainer(
            cards = listOf(
                FlashCardEntity(
                    frontText = "Hustle",
                    backText = "Full of activity"
                ),
                FlashCardEntity(
                    frontText = "serene",
                    backText = "calm, peaceful, and untroubled; tranquil"
                )
            )
        )
        log(tag = "decodeFromString") { "encodeToString" }

        val cardJson = "{\"frontText\":\"Hustle\",\"backText\":\"Full of activity\" }"

        try {
//            val jsonString = Json.encodeToString(listOfCards)
//            log(tag = "decodeFromString") { "jsonString: $jsonString" }

            val card = Json.decodeFromString<FlashCardEntity>(cardJson)
//            val cards = Json.decodeFromString<CardsContainer>(jsonString)
            log(tag = "decodeFromString") { "card: $card" }
//            log(tag = "decodeFromString") { "cards: $cards" }
        } catch (e: Exception) {
            log(tag = "decodeFromString") { "e: $e" }
        }

    }

    private val sharedFileReader: SharedFileReader = SharedFileReader()
    fun loadAndParseJsonFile() {
        val jsonString = sharedFileReader.loadJsonFile("commonWords.json")
//        val commonWords = sharedFileReader.loadJsonFile("commonWords.json")

        log(tag = "decodeFromString") { "jsonString: $jsonString" }
    }

    fun deleteBoard(boardId: String) = safeLaunch {
        getResultFlow { deleteBoardUseCase(boardId) }
            .onFailure { showError(SharedRes.strings.remove_record_error) }
            .onSuccess {}
    }

    private fun showError(message: StringResource) = safeLaunch {
//        uiState.update {
//            it.copy(
//                isLoading = false,
//                userMessage = message
//            )
//        }
    }
}