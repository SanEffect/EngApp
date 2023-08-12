package com.san.englishbender.ui

import com.san.englishbender.core.WhileUiSubscribed
import com.san.englishbender.data.local.dataStore.IDataStore
import com.san.englishbender.domain.usecases.labels.GetAllLabelsUseCase
import com.san.englishbender.domain.usecases.labels.SaveLabelUseCase
import database.Label
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update


class LabelsViewModel(
    val dataStore: IDataStore,
    val getAllLabelsUseCase: GetAllLabelsUseCase,
    private val saveLabelUseCase: SaveLabelUseCase
) : ViewModel() {

    data class LabelsUiState(
        val labels: List<Label> = emptyList(),
        val labelColors: List<String> = emptyList(),
        val isSaved: Boolean = false,
        val isLoading: Boolean = true
    )

    private val _uiState = MutableStateFlow(LabelsUiState())
    val uiState: StateFlow<LabelsUiState> = _uiState.asStateFlow()

//    val labels: StateFlow<LabelsUiState> = getAllLabelsUseCase()
//        .map {
//            LabelsUiState(labels = it)
//        }.stateIn(
//            scope = viewModelScope,
//            started = WhileUiSubscribed,
//            initialValue = LabelsUiState()
//        )

//    val uiState: StateFlow<LabelsUiState> = combine(
//        getAllLabelsUseCase(), dataStore.getUserSettingsAsFlow()
//    ) { labels, userSettings ->
//        LabelsUiState(
//            labels = labels,
//            labelColors = userSettings.labelColors
//        )
//    }
//        .stateIn(
//            scope = viewModelScope,
//            started = WhileUiSubscribed,
//            initialValue = LabelsUiState()
//        )

    fun getLabels() = safeLaunch {
        getAllLabelsUseCase().collect { labels ->
            _uiState.update {
                it.copy(
                    labels = labels,
                    isLoading = false
                )
            }
        }
    }

    fun getLabelColors() = safeLaunch {
        dataStore.getUserSettingsAsFlow().collect { userSettings ->
            _uiState.update {
                it.copy(
                    labelColors = userSettings.labelColors,
                    isLoading = false
                )
            }
        }
    }

    fun saveLabel(label: Label) = safeLaunch {
        execute(saveLabelUseCase(SaveLabelUseCase.Params(label))) {
            // navigate back
        }
    }
}