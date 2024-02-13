package com.san.englishbender.ui.recordDetails

import androidx.compose.runtime.mutableStateOf
import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.logging.LogLevel
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.aallam.openai.client.OpenAIConfig
import com.san.englishbender.SharedRes
import com.san.englishbender.core.Event
import com.san.englishbender.core.navigation.Navigator
import com.san.englishbender.data.getResultFlow
import com.san.englishbender.data.onFailure
import com.san.englishbender.data.onSuccess
import com.san.englishbender.domain.entities.RecordEntity
import com.san.englishbender.domain.entities.TagEntity
import com.san.englishbender.domain.entities.isNotEqual
import com.san.englishbender.domain.usecases.records.GetRecordFlowUseCase
import com.san.englishbender.domain.usecases.records.SaveRecordUseCase
import com.san.englishbender.domain.usecases.stats.UpdateStatsUseCase
import com.san.englishbender.domain.usecases.tags.GetTagsFlowUseCase
import com.san.englishbender.ui.ViewModel
import dev.icerock.moko.resources.StringResource
import io.github.aakira.napier.log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update

data class DetailUiState(
    val saveInProgress: Boolean = false,
    val record: RecordEntity = RecordEntity(),
    val tags: List<TagEntity> = emptyList(),
    val userMessage: Event<StringResource?> = Event(null)
)

data class GrammarCheckUiState(
    val isLoading: Boolean = false,
    val result: MutableList<String> = mutableListOf()
)

class RecordDetailsViewModel(
    private val getRecordFlowUseCase: GetRecordFlowUseCase,
    private val saveRecordUseCase: SaveRecordUseCase,
    private val updateStatsUseCase: UpdateStatsUseCase,
    private val getTagsFlowUseCase: GetTagsFlowUseCase,
    private val navigator: Navigator
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    private val _grammarUiState = MutableStateFlow(GrammarCheckUiState())
    val grammarUiState: StateFlow<GrammarCheckUiState> = _grammarUiState.asStateFlow()

    private var saveInProgress = false
    private var prevRecordState: RecordEntity? = null
    private var prevText: String = ""
    private val results = mutableListOf<String>()

    fun getRecord(recordId: String) {
        combine(
            getRecordFlowUseCase(recordId),
            getTagsFlowUseCase()
        ) { recordEntity, tags ->
            _uiState.update { state ->
                recordEntity?.let { record ->
                    prevRecordState = record.copy()
                    state.copy(
                        record = record,
                        tags = tags
                    )
                } ?: state.copy(tags = tags)
            }
        }.launchIn(viewModelScope)
    }

    fun saveRecord(
        currRecordState: RecordEntity,
        selectedTags: List<TagEntity>
    ) = safeLaunch {
        if (currRecordState.title.trim().isEmpty()) {
            showUserMessage(SharedRes.strings.empty_title_message)
            return@safeLaunch
        }
        if (saveInProgress) return@safeLaunch

        saveInProgress = true
        currRecordState.isDraft = false
        currRecordState.tags = selectedTags

        getResultFlow { saveRecordUseCase(currRecordState) }
            .onFailure {
                saveInProgress = false
                showUserMessage(SharedRes.strings.save_record_error)
            }
            .onSuccess {
                updateStatsUseCase(
                    prevRecordState = prevRecordState,
                    currRecordState = currRecordState
                )
                saveInProgress = false
//                prevRecordState = currRecordState
                navigator.popBackStack()
            }
    }

    fun resetUiState() = safeLaunch {
        _uiState.update {
            it.copy(record = RecordEntity())
        }
    }

    private fun showUserMessage(message: StringResource) {
        _uiState.update {
            it.copy(userMessage = Event(message))
        }
    }

    fun saveDraft(currRecordState: RecordEntity) = safeLaunch {

        log(tag = "saveDraft") { "currRecordState: $currRecordState" }
        log(tag = "saveDraft") { "prevRecordState: $prevRecordState" }

        val title = currRecordState.title.trim()
        val description = currRecordState.text.trim()

        if (title.isEmpty() && description.isEmpty()) return@safeLaunch

        prevRecordState?.let {
            val isEqual = currRecordState.isNotEqual(it)
            log(tag = "saveDraft") { "isEqual: $isEqual" }

            if (currRecordState.isNotEqual(it)) {
                currRecordState.isDraft = true
                saveRecordUseCase(currRecordState)
            }
        }
    }

    @OptIn(BetaOpenAI::class)
    fun checkGrammar(text: String) = safeLaunch {

        if (prevText.equals(text, true)) {
            _grammarUiState.update {
                it.copy(
                    isLoading = false,
                    result = results
                )
            }
            return@safeLaunch
        }

        prevText = text

        _grammarUiState.update { it.copy(isLoading = true) }
        val token = ""

        try {
            val config = OpenAIConfig(token, LogLevel.All)
            val openAI = OpenAI(config)

            val chatCompletionRequest = ChatCompletionRequest(
                model = ModelId("gpt-3.5-turbo"),
                messages = listOf(
                    ChatMessage(
                        role = ChatRole.System,
                        content = "You are a useful assistant for checking the grammar of English texts."
                    ),
                    ChatMessage(
                        role = ChatRole.User,
                        content = "Please, conduct a grammar check for this text - \"$text\". Confirm if it is grammatically correct, suggest improvements, or point out errors. Don't repeat this sentence."
                    )
                )
            )
            openAI.chatCompletion(chatCompletionRequest).choices.forEach {
                results.add(it.message?.content.toString())
            }
            _grammarUiState.update {
                it.copy(
                    isLoading = false,
                    result = results
                )
            }
        } catch (e: Exception) {
            log(tag = "GrammarCheckBSContent") { "OpenAI request error: $e" }
        }
    }
}
