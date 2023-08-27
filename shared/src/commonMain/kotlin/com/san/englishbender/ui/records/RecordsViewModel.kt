package com.san.englishbender.ui.records

import com.san.englishbender.core.WhileUiSubscribed
import com.san.englishbender.domain.entities.RecordEntity
import com.san.englishbender.domain.usecases.records.GetRecordsUseCase
import com.san.englishbender.domain.usecases.records.RemoveRecordUseCase
import com.san.englishbender.ui.ViewModel
import database.Label
import io.github.aakira.napier.log
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


data class RecordsUiState(
    val isLoading: Boolean = false,
    val records: List<RecordEntity> = emptyList(),
    val labels: List<Label> = emptyList()
)

class RecordsViewModel constructor(
    private val getRecordsUseCase: GetRecordsUseCase,
    private val removeRecordUseCase: RemoveRecordUseCase,
) : ViewModel() {

    val recordsUiState: StateFlow<RecordsUiState> =
        getRecordsUseCase(forceUpdate = false)
            .map { RecordsUiState(records = it) }
            .stateIn(
                scope = viewModelScope,
                started = WhileUiSubscribed,
                initialValue = RecordsUiState(isLoading = true)
            )

//    fun saveRecords() = safeLaunch {
//        val records = mutableListOf<RecordDto>()
//        repeat(100) { i ->
//            records.add(
//                RecordDto(
//                    id = UUID.randomUUID().toString(),
//                    title = "$i",
//                    creationDate = System.currentTimeMillis() + i
//                )
//            )
//        }
//
//        recordDao.insert(records)
//    }

    fun removeRecord(record: RecordEntity) = safeLaunch {
        removeRecordUseCase(record).let { result ->
            log { "result: $result" }

//            when (result) {
////                is Success -> loadRecordsPaging()
////                is Failure -> setState(BaseViewState.Error(result.exception))
//                else -> {}
//            }
        }
    }
}
