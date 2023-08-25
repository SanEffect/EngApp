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
import com.san.englishbender.domain.entities.LabelEntity
import com.san.englishbender.domain.entities.RecordEntity
import com.san.englishbender.domain.usecases.labels.GetLabelsFlowUseCase
import com.san.englishbender.domain.usecases.records.GetRecordWithLabels
import com.san.englishbender.domain.usecases.records.SaveRecordLabelUseCase
import com.san.englishbender.domain.usecases.records.SaveRecordUseCase
import com.san.englishbender.domain.usecases.stats.UpdateStatsUseCase
import com.san.englishbender.ui.ViewModel
import database.RecordLabelCrossRef
import database.Stats
import io.github.aakira.napier.log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DetailUiState(
    val record: RecordEntity? = null,
    val labels: List<LabelEntity> = emptyList()
)

class RecordDetailViewModel constructor(
    private val getRecordWithLabels: GetRecordWithLabels,
    private val saveRecordUseCase: SaveRecordUseCase,
    private val updateStatsUseCase: UpdateStatsUseCase,
    private val getLabelsFlowUseCase: GetLabelsFlowUseCase,
    private val saveRecordLabelUseCase: SaveRecordLabelUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    val randomGreeting = AppConstants.GREETINGS.random()

    private var stats: Stats? = null
    private var currentRecord: RecordEntity? = null

    private var currentWordsCount = 0
    private var currentLettersCount = 0

//    val detailUiState: StateFlow<DetailUiState> = combine(
//        getRecordWithLabels(recordId),
////        getRecordFlowUseCase(recordId),
//        getLabelsFlowUseCase()
//    ) { recordEntity, labels ->
//
//        log(tag = "selectedLabels") { "recordId: $recordId" }
//        log(tag = "selectedLabels") { "recordEntity: $recordEntity" }
//        log(tag = "selectedLabels") { "recordEntity.labels: ${recordEntity?.labels}" }
//
////        recordEntity?.labels = labels.filter { it.contains(it.id) }
//
//
//        DetailUiState(
//            record = recordEntity,
//            labels = labels
//        )
//    }
//        .stateIn(
//            scope = viewModelScope,
//            started = WhileUiSubscribed,
//            initialValue = DetailUiState()
//        )

    fun getRecord(recordId: String?) =
        combine(
            getRecordWithLabels(recordId),
            getLabelsFlowUseCase()
        ) { record, labels ->
            _uiState.update {
                it.copy(
                    record = record,
                    labels = labels
                )
            }
        }.launchIn(viewModelScope)

    fun saveRecord(
        record: RecordEntity,
        selectedLabels: List<LabelEntity>
    ) = safeLaunch {
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
        val wordsCount = getDiff(currentWordsCount, getWordsCountOfStrings(title, description))
        val lettersCount = getDiff(currentLettersCount, getLettersOfStrings(title, description))

        currentWordsCount = 0
        currentLettersCount = 0

        execute(saveRecordUseCase(SaveRecordUseCase.Params(record))) { recordId ->
            updateRecordLabels(
                isNewRecord = record.id.isEmpty(),
                recordId = recordId,
                selectedLabels = selectedLabels
            )
            saveStats(wordsCount, lettersCount)
        }
    }

    private fun updateRecordLabels(
        isNewRecord: Boolean,
        recordId: String,
        selectedLabels: List<LabelEntity>
    ) = safeLaunch {
        when (isNewRecord) {
            true -> saveRecordLabes(recordId, selectedLabels)
            false -> {
                val initialLabels = _uiState.value.labels
                val deletedLabels = initialLabels.subtract(selectedLabels.toSet()).toList()
                val addedLabels = selectedLabels.subtract(initialLabels.toSet()).toList()

                deleteRecordLabels(recordId, deletedLabels)
                saveRecordLabes(recordId, addedLabels)
            }
        }
    }

    private fun deleteRecordLabels(recordId: String, labels: List<LabelEntity>) = safeLaunch {
        labels.forEach {
            // Delete labels by id
        }
    }

    private fun saveRecordLabes(recordId: String, labels: List<LabelEntity>) = safeLaunch {
        labels.forEach { label ->
            val params = SaveRecordLabelUseCase.Params(
                RecordLabelCrossRef(recordId = recordId, labelId = label.id)
            )
            execute(saveRecordLabelUseCase(params)) {
                log(tag = "saveRecordUseCase") { "recordLabels saved" }
            }
        }
    }

    private fun saveStats(wordsCount: Int, lettersCount: Int) = safeLaunch {
        val statParams = UpdateStatsUseCase.Params(
            recordsCount = 1,
            wordsCount = wordsCount,
            lettersCount = lettersCount
        )
        execute(updateStatsUseCase(statParams)) { result ->
            log(tag = "saveRecordUseCase") { "updateStatsUseCase result: $result" }
        }
    }

//    fun loadRecord(id: String) = safeLaunch {
//        val params = GetRecordUseCase.Params(id)
//        execute(getRecordUseCase(params)) { record ->
//            currentRecord = record?.copy()
//
//            currentRecord?.let {
//                currentWordsCount = getWordsCountOfStrings(it.title, it.description)
//                currentLettersCount = getLettersOfStrings(it.title, it.description)
//            }
//
//            detailUiState.update {
//
//            }
//
//            _uiState.emit(RecordsDetailUiState.Success(record))
//        }
//    }

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

    private fun getLettersOfString(value: String): Int = value.replace(" ", "").length
    private fun getLettersOfStrings(vararg values: String): Int {
        return values.sumOf { it.replace(" ", "").length }
    }

    private fun getWordsCountOfStrings(vararg values: String): Int {
        return values.sumOf { it.split(" ").size }
    }

    private fun getDiff(currentValue: Int, newValue: Int): Int {
        return newValue - currentValue
    }

    fun saveDraft(record: RecordEntity) = safeLaunch {
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

    private fun isThereSomeChanges(record: RecordEntity): Boolean {
        val currRecordTitle = currentRecord?.title?.trim()
        val currRecordDesc = currentRecord?.description?.trim()

        val newTitle = record.title.trim()
        val newDesc = record.description.trim()

//        val res = currentRecord?.equals(record)

        return (currRecordTitle != newTitle || currRecordDesc != newDesc)
    }

//    suspend fun showEmptyScreen() {
//        _uiState.emit(RecordsDetailUiState.Empty)
//    }

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
