package com.san.englishbender.ui.recordDetail

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.logging.LogLevel
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.aallam.openai.client.OpenAIConfig
import com.san.englishbender.SharedRes
import com.san.englishbender.core.AppConstants
import com.san.englishbender.core.Event
import com.san.englishbender.core.navigation.Navigator
import com.san.englishbender.data.getResultFlow
import com.san.englishbender.data.ifFailure
import com.san.englishbender.data.ifSuccess
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
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DetailUiState(
    val saveInProgress: Boolean = false,
    val record: RecordEntity = RecordEntity(),
    val tags: List<TagEntity> = emptyList(),
    val userMessage: Event<StringResource?> = Event(null)
)

class RecordDetailViewModel constructor(
    private val getRecordFlowUseCase: GetRecordFlowUseCase,
    private val saveRecordUseCase: SaveRecordUseCase,
    private val updateStatsUseCase: UpdateStatsUseCase,
    private val getTagsFlowUseCase: GetTagsFlowUseCase,
    private val navigator: Navigator
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    private var saveInProgress = false
    private var prevRecordState: RecordEntity? = null

    fun getRecord(recordId: String?) {
        val recId = recordId ?: return

        combine(
            getRecordFlowUseCase(recId),
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
            .ifFailure {
                saveInProgress = false
                showUserMessage(SharedRes.strings.save_record_error)
            }
            .ifSuccess {
                updateStatsUseCase(
                    prevRecordState = prevRecordState,
                    currRecordState = currRecordState
                )
                saveInProgress = false
                navigator.popBackStack()
            }
    }

//    private fun updateRecordTags(
//        recordId: String,
//        selectedTags: List<TagEntity>
//    ) = safeLaunch {
//        // Delete tags if necessary
//        _uiState.value.tags.let { initialTags ->
//            val deletedTags = initialTags.subtract(selectedTags.toSet()).toList()
//            deletedTags.forEach { tag ->
//                deleteByRecordTagIdUseCase(recordId, tag.id)
//            }
//        }
//    }

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

    fun saveDraft(record: RecordEntity) = safeLaunch {
        val title = record.title.trim()
        val description = record.description.trim()

        if (title.isEmpty() && description.isEmpty()) return@safeLaunch

        prevRecordState?.let {
            if (record.isNotEqual(it)) {
                record.isDraft = true
                saveRecordUseCase(record)
            }
        }
    }

    private val textResult = MutableStateFlow(listOf(""))

    @OptIn(BetaOpenAI::class)
    fun checkGrammar(text: String) = viewModelScope.launch {

        val token = ""

        try {

            val openAI = OpenAI(OpenAIConfig(token, LogLevel.None))

            //Timber.tag("openAI").d("> Getting available engines...")
            //openAI.models().forEach(::println)

            //Timber.tag("openAI").d("\n> Getting ada engine...")

//        val ada = openAI.model(modelId = ModelId("text-ada-001"))

//        Timber.tag("openAI").d("\n>️ Creating completion...")
//        val completionRequest = CompletionRequest(
//            model = ada.id,
//            prompt = "What is the difference between 'stare' and 'glance'?"
//        )
////        openAI.completion(completionRequest).choices.forEach(::println)
//        openAI.completion(completionRequest).choices.forEach {
//            Timber.tag("openAI").d("\n>️ ${it.text}")
//        }

            val chatCompletionRequest = ChatCompletionRequest(
                model = ModelId("gpt-3.5-turbo"),
                messages = listOf(
//                ChatMessage(
//                    role = ChatRole.System,
//                    content = "You are a helpful assistant that translates Russian to English."
//                ),
                    ChatMessage(
                        role = ChatRole.User,
                        content = "Please, do grammar check for this text - \"$text\""
                    )
                )
            )

            val list = mutableListOf("")
            openAI.chatCompletion(chatCompletionRequest).choices.forEach {

                list.add(it.message.toString())
//            list.add(it.message?.content.toString())

//            textResult.value = it.message?.content.toString()
            }
            textResult.value = list

        } catch (e: Exception) {

        }
//        val chatCompletionRequest = ChatCompletionRequest(
//            model = ModelId("gpt-3.5-turbo"),
////            model = ModelId("gpt-4"),
//            messages = listOf(
//                ChatMessage(
//                    role = ChatRole.User,
//                    content = "Hello Chat-GPT!"
//                )
//            )
//        )
//        val completion: ChatCompletion = openAI.chatCompletion(chatCompletionRequest)
    }

//    private var needToTranslate = true
//    private var translateToggled = false
//    private var translatedText = ""
//    private var originalText = ""
//
//    private fun getRussianWords(text: String): List<String> {
//        if (text.isEmpty()) return arrayListOf()
//
//        val words = text.split(" ")
//        return words.filter { langDetector.detectLanguageOf(it) == Language.RUSSIAN }
//    }
//
//    val description = MutableStateFlow("")

//    @FlowPreview
//    @ExperimentalCoroutinesApi
//    val russianWordList = description
//        .debounce(1000)
//        .distinctUntilChanged()
//        .flatMapLatest { text ->
//            flow { emit(getRussianWords(text)) }
//        }


    /*    private fun highlightRussianWords(text: String): AnnotatedString {
            val russianWords = getRussianWords(text)

            return buildAnnotatedString {
                append(text)

                russianWords.forEach { word ->
                    val startIndex = text.indexOf(word)
                    val endIndex = startIndex + word.length

                    addStyle(
                        style = SpanStyle(
                            background = Color(0xfffcfcb1),
                            color = Color(0xff64B5F6),
                            fontSize = 16.sp
                        ),
                        start = startIndex,
                        end = endIndex
                    )
                }

                // Add bold style to keywords that has to be bold
    //            boldIndexes.forEach {
    //                addStyle(
    //                    style = SpanStyle(
    //                        background = Color(0xfffcfcb1),
    //                        fontWeight = FontWeight.Bold,
    //                        color = Color(0xff64B5F6),
    //                        fontSize = 15.sp
    //
    //                    ),
    //                    start = it.first,
    //                    end = it.second
    //                )
    //            }
            }
        }*/
}
