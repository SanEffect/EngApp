package com.san.englishbender.ui

import com.san.englishbender.core.extensions.WhileUiSubscribed
import com.san.englishbender.data.local.dataStore.IDataStore
import com.san.englishbender.data.local.models.Tag
import com.san.englishbender.domain.entities.TagEntity
import com.san.englishbender.domain.usecases.tags.DeleteTagUseCase
import com.san.englishbender.domain.usecases.tags.GetTagsFlowUseCase
import com.san.englishbender.domain.usecases.tags.SaveTagColorUseCase
import com.san.englishbender.domain.usecases.tags.SaveTagUseCase
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn


data class TagsUiState(
    val tags: List<TagEntity> = emptyList(),
    val colorPresets: List<String> = emptyList(),
    val isSaved: Boolean = false
)

class TagsViewModel(
    private val dataStore: IDataStore,
    val getTagsFlowUseCase: GetTagsFlowUseCase,
    private val saveTagUseCase: SaveTagUseCase,
    private val saveTagColorUseCase: SaveTagColorUseCase,
    private val deleteTagUseCase: DeleteTagUseCase
) : ViewModel() {

//    private val _uiState = MutableStateFlow(TagsUiState())
//    val uiState: StateFlow<TagsUiState> = _uiState.asStateFlow()

//    val labels: StateFlow<LabelsUiState> = getAllLabelsUseCase()
//        .map {
//            LabelsUiState(labels = it)
//        }.stateIn(
//            scope = viewModelScope,
//            started = WhileUiSubscribed,
//            initialValue = LabelsUiState()
//        )

    val uiState: StateFlow<TagsUiState> = combine(
        getTagsFlowUseCase(),
        dataStore.getAppSettingsFlow()
    ) { tags, appSettings ->
        TagsUiState(
            tags = tags,
            colorPresets = appSettings.colorPresets
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = WhileUiSubscribed,
            initialValue = TagsUiState()
        )

//    fun getTags() = safeLaunch {
//        getTagsFlowUseCase().collect { tags ->
//            _uiState.update {
//                it.copy(tags = tags)
//            }
//        }
//    }
//
//    fun getColorPresets() = safeLaunch {
//        val appSettings = dataStore.getAppSettings()
//        _uiState.update {
//            it.copy(colorPresets = appSettings.colorPresets)
//        }
//    }

    suspend fun saveTag(tag: Tag) = safeLaunch {
        saveTagUseCase(tag)
    }

    suspend fun deleteTag(tagId: String) = safeAsync {
        deleteTagUseCase(tagId)
    }

    suspend fun saveTagColor(hexCode: String) = safeAsync {
        saveTagColorUseCase(hexCode)
    }
}