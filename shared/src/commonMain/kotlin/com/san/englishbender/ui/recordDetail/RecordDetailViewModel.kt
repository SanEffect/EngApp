package com.san.englishbender.ui.recordDetail

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.logging.LogLevel
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.aallam.openai.client.OpenAIConfig
import com.san.englishbender.core.AppConstants
//import com.san.englishbender.core.Event
import com.san.englishbender.domain.entities.Record
import com.san.englishbender.domain.usecases.GetRecordUseCase
import com.san.englishbender.domain.usecases.SaveRecordUseCase
import com.san.englishbender.ui.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


sealed class NavTarget {
    object RecordsScreen : NavTarget()
}


sealed interface RecordsDetailUiState {

    object Loading : RecordsDetailUiState
    object Empty : RecordsDetailUiState

    data class Success(val record: Record?) : RecordsDetailUiState

    data class Failure(val exception: Exception) : RecordsDetailUiState
}

class RecordDetailViewModel constructor(
    private val getRecordUseCase: GetRecordUseCase,
    private val saveRecordUseCase: SaveRecordUseCase,
//    private val recordDao: RecordDao,
//    private val labelDao: LabelDao,
//    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<RecordsDetailUiState>(RecordsDetailUiState.Loading)
    val uiState = _uiState.asStateFlow()

//    val taskId: String = savedStateHandle[DestinationsArgs.RECORD_ID_ARG]!!

//    private val _snackBar = MutableStateFlow<Event<String?>>(Event(null))
//    private val _snackBar: MutableSharedFlow<String> = MutableStateFlow("")
//    val snackbar = _snackBar.asStateFlow()

//    private val _navigation = MutableStateFlow<Event<NavTarget?>>(Event(null))
//    val navigation = _navigation.asStateFlow()

    val randomGreeting = AppConstants.GREETINGS.random()

//    init {
//        setState(BaseViewState.Loading)
//    }

    private var currentRecord: Record? = null

    fun loadRecord(id: String) = safeLaunch {
//        execute(recordsRepository.getRecordById(id, false)) { record ->
//            currentRecord = record.copy()
//            setState(BaseViewState.Data(RecordDetailState(record)))
//        }

        val params = GetRecordUseCase.Params(id)
        execute(getRecordUseCase(params)) { record ->
            currentRecord = record?.copy()

            _uiState.emit(RecordsDetailUiState.Success(record))
        }
    }

    val textResult = MutableStateFlow(listOf(""))

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

    fun insertTags() = viewModelScope.launch {
        try {
//            val tagId1 = UUID.randomUUID().toString()
//            val tagId2 = UUID.randomUUID().toString()
//            val tag1 = RecordLabelDto(tagId1, "Thoughts")
//            val tag2 = RecordLabelDto(tagId2, "Feelings")
//
//            val recId1 = UUID.randomUUID().toString()
//            val recId2 = UUID.randomUUID().toString()
//            val rec1 = RecordDto(recId1, "Record1")
//            val rec2 = RecordDto(recId2, "Record2")
//
//            recordDao.insert(rec1)
//            recordDao.insert(rec2)
//
//            recordLabelDao.insert(tag1)
//            recordLabelDao.insert(tag2)
//
//            val crossRef1 = RecordLabelCrossRef(recId1, tagId1)
//            val crossRef2_1 = RecordLabelCrossRef(recId2, tagId1)
//            val crossRef2_2 = RecordLabelCrossRef(recId2, tagId2)
//
//            recordDao.insertRecordLabelCrossRef(crossRef1)
//            recordDao.insertRecordLabelCrossRef(crossRef2_1)
//            recordDao.insertRecordLabelCrossRef(crossRef2_2)

            // ---
//            val labels = labelDao.getLabels()
//            val labelIds = labels.map { it.id }
//
//            val allRecords = recordDao.getRecordsByTags(labelIds)
//            val recordsWithTag1 = recordDao.getRecordsByTags(listOf(labelIds[0]))
//            val recordsWithTag2 = recordDao.getRecordsByTags(listOf(labelIds[1]))
//
//            Timber.tag("insertTags").d("labelIds: $labelIds")
//            Timber.tag("insertTags").d("allRecords: $allRecords")
//            Timber.tag("insertTags").d("recordsWithTag1: $recordsWithTag1")
//            Timber.tag("insertTags").d("recordsWithTag2: $recordsWithTag2")
        } catch (e: Exception) {

        }
    }

    fun saveRecord(record: Record) = safeLaunch {
        val title = record.title.trim()
        val description = record.description.trim()

        if (title.isEmpty() || description.isEmpty()) {
//            Timber.tag("recordDesc").d("currentTitle: $currentTitle")
//            Timber.tag("recordDesc").d("currentDescription: $currentDescription")
//            _snackbarText.value = Event(R.string.empty_record_message)
//            _snackBar.value = stringResource(id = R.string.empty_record_message)
//            _snackBar.value = Event("Empty title or description")
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
//            _navigation.value = Event(NavTarget.RecordsScreen)
        }
    }

    fun saveDraft(record: Record) = safeLaunch {
        val title = record.title.trim()
        val description = record.description.trim()

        if (title.isEmpty() && description.isEmpty()) return@safeLaunch

        // Check difference
        if (currentRecord?.title == title && currentRecord?.description == description) return@safeLaunch

        record.isDraft = true

//        execute(saveRecordUseCase(SaveRecordUseCase.Params(record))) {
////            navigationManager.navigate(NavigationDirection.records)
//        }
    }

    private fun isThereSomeChanges(dto: Record): Boolean {
        val currRecordTitle = currentRecord?.title?.trim()
        val currRecordDesc = currentRecord?.description?.trim()

        val newTitle = dto.title.trim()
        val newDesc = dto.description.trim()

        return (currRecordTitle != newTitle || currRecordDesc != newDesc)
    }

    suspend fun showEmptyScreen() {
        _uiState.emit(RecordsDetailUiState.Empty)
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
