package com.san.englishbender.ui.records

import com.san.englishbender.SharedRes
import com.san.englishbender.core.extensions.WhileUiSubscribed
import com.san.englishbender.data.getResultFlow
import com.san.englishbender.data.ifFailure
import com.san.englishbender.domain.entities.RecordEntity
import com.san.englishbender.domain.usecases.records.GetRecordsUseCase
import com.san.englishbender.domain.usecases.records.RemoveRecordUseCase
import com.san.englishbender.domain.usecases.records.SaveRecordUseCase
import com.san.englishbender.ui.ViewModel
import database.Label
import dev.icerock.moko.resources.StringResource
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


data class RecordsUiState(
    val isLoading: Boolean = false,
    val records: List<RecordEntity> = emptyList(),
    val labels: List<Label> = emptyList(),
    val userMessage: StringResource? = null
)

class RecordsViewModel constructor(
    private val getRecordsUseCase: GetRecordsUseCase,
    private val saveRecordUseCase: SaveRecordUseCase,
    private val removeRecordUseCase: RemoveRecordUseCase
) : ViewModel() {

    val uiState: StateFlow<RecordsUiState> =
        getRecordsUseCase(forceUpdate = false)
            .map { RecordsUiState(records = it) }
            .catch { RecordsUiState(userMessage = SharedRes.strings.loading_records_error) }
            .stateIn(
                scope = viewModelScope,
                started = WhileUiSubscribed,
                initialValue = RecordsUiState(isLoading = true)
            )

//    fun saveRecords() = safeLaunch {
//        repeat(200) { i ->
//            val n = i + 79
//            val rec = RecordEntity(
//                id = "",
//                title = "Title $n",
//                description = "Description"
//            )
//            saveRecordUseCase(rec)
//        }
//    }

    fun removeRecord(record: RecordEntity) = safeLaunch {
        getResultFlow { removeRecordUseCase(record) }
            .ifFailure { RecordsUiState(userMessage = SharedRes.strings.remove_record_error) }
    }
}
