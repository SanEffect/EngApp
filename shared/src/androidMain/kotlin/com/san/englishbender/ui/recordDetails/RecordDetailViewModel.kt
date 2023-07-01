package com.san.englishbender.ui.recordDetails

import androidx.lifecycle.viewModelScope
import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.logging.LogLevel
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.aallam.openai.client.OpenAIConfig
import com.san.englishbender.core.AppConstants
import com.san.englishbender.core.Event
import com.san.englishbender.domain.entities.Record
import com.san.englishbender.domain.usecases.GetRecordUseCase
import com.san.englishbender.domain.usecases.SaveRecordUseCase
import com.san.englishbender.ui.common.base.BaseViewState
import com.san.englishbender.ui.common.base.MviViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

sealed class NavTarget {
    object RecordsScreen : NavTarget()
}


class RecordDetailViewModel constructor(
    private val getRecordUseCase: GetRecordUseCase,
    private val saveRecordUseCase: SaveRecordUseCase,
) : MviViewModel<BaseViewState<RecordDetailState>>() {

    private val _snackBar: MutableStateFlow<Event<String?>> = MutableStateFlow(Event(null))
//    private val _snackBar: MutableSharedFlow<String> = MutableStateFlow("")
    val snackbar = _snackBar.asStateFlow()

    private val _navigation: MutableStateFlow<Event<NavTarget?>> = MutableStateFlow(Event(null))
    val navigation = _navigation.asStateFlow()

    val randomGreeting = AppConstants.GREETINGS.random()

    init {
        setState(BaseViewState.Loading)
    }

    private var currentRecord: Record? = null

    fun loadRecord(id: String) = safeLaunch {
//        execute(recordsRepository.getRecordById(id, false)) { record ->
//            currentRecord = record.copy()
//            setState(BaseViewState.Data(RecordDetailState(record)))
//        }

        val params = GetRecordUseCase.Params(id)
        execute(getRecordUseCase(params)) { recordDto ->
            currentRecord = recordDto.copy()
            setState(BaseViewState.Data(RecordDetailState(recordDto)))
            Timber.tag("recordDesc").d("id: $id")
        }
    }

    val textResult = MutableStateFlow(listOf(""))

    @OptIn(BetaOpenAI::class)
    fun checkGrammar(text: String) = viewModelScope.launch {
        val token = "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"

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
            Timber.tag("openAI").d("\n>️ ${it.message}")
            list.add(it.message.toString())
        }
        textResult.value = list
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

    override fun onCleared() {
        super.onCleared()
        Timber.tag("recordDesc").d("onCleared")
        setState(BaseViewState.Loading)
    }

    fun saveRecord(record: Record) = safeLaunch {
        val title = record.title.trim()
        val description = record.description.trim()

        if (title.isEmpty() || description.isEmpty()) {
//            Timber.tag("recordDesc").d("currentTitle: $currentTitle")
//            Timber.tag("recordDesc").d("currentDescription: $currentDescription")
//            _snackbarText.value = Event(R.string.empty_record_message)
//            _snackBar.value = stringResource(id = R.string.empty_record_message)
            _snackBar.value = Event("Empty title or description")
            return@safeLaunch
        }

        record.isDraft = false

//        val recDto = RecordDto(
//            title = "Test",
//            description = "TestDesc"
//        )
//
//        recordsRepository.saveRecord(recDto)

        execute(saveRecordUseCase(SaveRecordUseCase.Params(record))) {
            _navigation.value = Event(NavTarget.RecordsScreen)
        }
    }

    fun saveDraft(record: Record) = safeLaunch {
        val title = record.title.trim()
        val description = record.description.trim()

        if (title.isEmpty() && description.isEmpty()) return@safeLaunch

        // Check difference
        if (currentRecord?.title == title && currentRecord?.description == description) return@safeLaunch

        record.isDraft = true

        execute(saveRecordUseCase(SaveRecordUseCase.Params(record))) {
//            navigationManager.navigate(NavigationDirection.records)
        }
    }

    private fun isThereSomeChanges(dto: Record): Boolean {
        val currRecordTitle = currentRecord?.title?.trim()
        val currRecordDesc = currentRecord?.description?.trim()

        val newTitle = dto.title.trim()
        val newDesc = dto.description.trim()

        return (currRecordTitle != newTitle || currRecordDesc != newDesc)
    }

    fun showEmptyScreen() {
        setState(BaseViewState.Empty)
//        setState(BaseViewState.Data(RecordDetailState()))
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