package com.san.englishbender.ui

import com.san.englishbender.data.local.dataStore.IDataStore
import com.san.englishbender.data.local.models.Tag
import com.san.englishbender.domain.entities.TagEntity
import com.san.englishbender.domain.usecases.tags.GetTagsFlowUseCase
import com.san.englishbender.domain.usecases.tags.SaveTagUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


data class TagsUiState(
    val tags: List<TagEntity> = emptyList(),
    val tagColors: List<String> = emptyList(),
    val isSaved: Boolean = false,
    val isLoading: Boolean = true
)

class TagsViewModel(
    private val dataStore: IDataStore,
    val getTagsFlowUseCase: GetTagsFlowUseCase,
    private val saveTagUseCase: SaveTagUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TagsUiState())
    val uiState: StateFlow<TagsUiState> = _uiState.asStateFlow()

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

    fun getTags() = safeLaunch {
        getTagsFlowUseCase().collect { tags ->
            _uiState.update {
                it.copy(
                    tags = tags,
                    isLoading = false
                )
            }
        }
    }

    fun getTagColors() = safeLaunch {
        dataStore.getUserSettingsAsFlow().collect { userSettings ->
            _uiState.update {
                it.copy(
                    tagColors = userSettings.tagColors,
                    isLoading = false
                )
            }
        }
    }

    fun saveTag(tag: Tag) = safeLaunch {
        saveTagUseCase(tag)
    }
}