package com.san.englishbender.ui.records

import com.san.englishbender.domain.entities.Record
import com.san.englishbender.domain.usecases.GetRecordsUseCase
import com.san.englishbender.domain.usecases.RemoveRecordUseCase
import com.san.englishbender.ui.ViewModel
import io.github.aakira.napier.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


data class RecordsState(
    val isLoading: Boolean = false,
    val records: List<Record> = emptyList(),
//    val records: Flow<PagingData<Record>> = emptyFlow(),
    val title: String = "",
    val description: String = "",
)

sealed interface RecordsUiState {
    object Loading : RecordsUiState
    data class Success(
//        val records: Flow<List<Record>> = emptyFlow()
        val records: List<Record> = emptyList()
    ) : RecordsUiState
    data class Failure(val exception: Exception) : RecordsUiState
}

inline fun <reified T> Any?.toFlow(): Flow<T> = flow { emit(this@toFlow as T) }

class RecordsViewModel constructor(
    private val getRecordsUseCase: GetRecordsUseCase,
    private val removeRecordUseCase: RemoveRecordUseCase,
) : ViewModel() {

//    private val _viewState = MutableStateFlow(RecordsState())
//    val viewState = _viewState.asStateFlow()

    private val _uiState = MutableStateFlow<RecordsUiState>(RecordsUiState.Loading)
    val uiState = _uiState.asStateFlow()

//    private val config = PagingConfig(
//        pageSize = 10,
//        initialLoadSize = 10,
//        prefetchDistance = 10
//    )

    val recordsUiState: StateFlow<RecordsUiState> =
        getRecordsUseCase(forceUpdate = false)
            .map {
                val isEmpty = it.isEmpty()
                log { "records: $it" }
                log { "isEmpty: $isEmpty" }
                log { "records.size: ${it.size}" }
//                RecordsUiState.Success(records = it.toFlow())
                RecordsUiState.Success(records = it)
//                RecordsState(records = it.toFlow())
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = RecordsUiState.Loading
//                initialValue = RecordsState(isLoading = true)
            )

//    fun loadRecordsPaging(force: Boolean = false) = safeLaunch {
//        val params = GetRecordsPagingUseCase.Params(force, config)
//        val paged = getRecordsPagingUseCase(params).cachedIn(scope = viewModelScope)
//
//        _uiState.value = RecordsUiState.Success(records = paged)
//    }

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

//    fun loadRecords() = safeLaunch {
//        setState(BaseViewState.Loading)
//
//        val params = GetRecordsUseCase.Params()
//        execute(getRecordsUseCase(params)) {
//            setState(BaseViewState.Data(GetRecordsState(records = it)))
//        }
//    }

    private fun removeRecord(id: String) = safeLaunch {
//        call(removeRecordUseCase(RemoveRecordUseCase.Params(id))) { result ->
//            when (result) {
////                is Success -> loadRecordsPaging()
////                is Failure -> setState(BaseViewState.Error(result.exception))
//                else -> {}
//            }
//        }
    }
}

/*
class RecordsViewModelFactory @Inject constructor(
    private val removeRecordUseCase: RemoveRecordUseCase,
    private val saveRecordUseCase: SaveRecordUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass != RecordsViewModelFactory::class.java) {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
        return RecordsViewModelFactory(
            removeRecordUseCase,
            saveRecordUseCase
        ) as T
    }
}*/
