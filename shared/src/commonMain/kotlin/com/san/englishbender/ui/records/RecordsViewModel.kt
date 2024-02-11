package com.san.englishbender.ui.records

import com.san.englishbender.SharedRes
import com.san.englishbender.core.extensions.WhileUiSubscribed
import com.san.englishbender.data.getResultFlow
import com.san.englishbender.data.onFailure
import com.san.englishbender.data.onSuccess
import com.san.englishbender.domain.entities.RecordEntity
import com.san.englishbender.domain.entities.TagEntity
import com.san.englishbender.domain.usecases.records.GetRecordsUseCase
import com.san.englishbender.domain.usecases.records.RemoveRecordUseCase
import com.san.englishbender.ui.ViewModel
import dev.icerock.moko.resources.StringResource
import io.github.aakira.napier.log
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


data class RecordsUiState(
    val isLoading: Boolean = false,
    val records: List<RecordEntity> = emptyList(),
    val tags: List<TagEntity> = emptyList(),
    val userMessage: StringResource? = null
)

class RecordsViewModel(
    private val getRecordsUseCase: GetRecordsUseCase,
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

    fun removeRecord(record: RecordEntity) = safeLaunch {
        getResultFlow { removeRecordUseCase(record) }
            .onFailure { RecordsUiState(userMessage = SharedRes.strings.remove_record_error) }
            .onSuccess {
                log(tag = "ExceptionHandling") { "getResultFlow success" }
            }
    }
}
