package com.san.englishbender.ui.records

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.san.englishbender.data.Result.Success
import com.san.englishbender.data.Result.Failure
import com.san.englishbender.ui.common.base.BaseViewState
import com.san.englishbender.ui.common.base.MviViewModel
import com.san.englishbender.domain.usecases.GetRecordsPagingUseCase
import com.san.englishbender.domain.usecases.GetRecordsUseCase
import com.san.englishbender.domain.usecases.RemoveRecordUseCase


class RecordsViewModel constructor(
    private val getRecordsPagingUseCase: GetRecordsPagingUseCase,
//    private val getRecordsUseCase: GetRecordsUseCase,
    private val removeRecordUseCase: RemoveRecordUseCase,
) : MviViewModel<BaseViewState<GetRecordsState>>() {

    private val config = PagingConfig(pageSize = 10)

    fun loadRecordsPaging(force: Boolean = false) = safeLaunch {
//        setState(BaseViewState.Loading)
//
//        val paged = recordsRepository.getRecordsPaging(config).cachedIn(viewModelScope)
//        setState(BaseViewState.Data(GetRecordsState(pagedData = paged)))

        setState(BaseViewState.Loading)
        val params = GetRecordsPagingUseCase.Params(force, config)
        val paged = getRecordsPagingUseCase(params).cachedIn(scope = viewModelScope)

        setState(BaseViewState.Data(GetRecordsState(pagedData = paged)))
    }

//    fun loadRecords() = safeLaunch {
//        setState(BaseViewState.Loading)
//
//        val params = GetRecordsUseCase.Params()
//        execute(getRecordsUseCase(params)) {
//            setState(BaseViewState.Data(GetRecordsState(records = it)))
//        }
//    }

    private fun removeRecord(id: String) = safeLaunch {
        call(removeRecordUseCase(RemoveRecordUseCase.Params(id))) { result ->
            when(result) {
                is Success -> loadRecordsPaging()
                is Failure -> setState(BaseViewState.Error(result.exception))
            }
        }
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
