package com.san.englishbender.ui

import androidx.compose.runtime.collectAsState
import com.san.englishbender.core.WhileUiSubscribed
import com.san.englishbender.domain.usecases.labels.GetAllLabelsUseCase
import com.san.englishbender.domain.usecases.labels.SaveLabelUseCase
import com.san.englishbender.ui.records.RecordsUiState
import database.Label
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


data class LabelsUiState(
    val labels: List<Label> = emptyList(),
    val isSaved: Boolean = false
)

class LabelsViewModel(
    private val getAllLabelsUseCase: GetAllLabelsUseCase,
    private val saveLabelUseCase: SaveLabelUseCase
) : ViewModel() {

    val uiState: StateFlow<LabelsUiState> = getAllLabelsUseCase().map {
        LabelsUiState(labels = it)
    }.stateIn(
        scope = viewModelScope,
        started = WhileUiSubscribed,
        initialValue = LabelsUiState()
    )

    fun saveLabel(label: Label) = safeLaunch {
        execute(saveLabelUseCase(SaveLabelUseCase.Params(label))) {
            // navigate back
        }
    }
}