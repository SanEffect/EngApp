package com.san.englishbender.android.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.san.englishbender.android.ui.MainActivityUiState.Loading
import com.san.englishbender.android.ui.MainActivityUiState.Success
import com.san.englishbender.data.local.models.UserSettings
import com.san.englishbender.domain.repositories.IUserDataRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


class MainActivityViewModel constructor(
    userDataRepository: IUserDataRepository,
) : ViewModel() {
    val uiState: StateFlow<MainActivityUiState> = userDataRepository.userData.map {
        Success(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = Loading,
        started = SharingStarted.WhileSubscribed(5_000),
    )
}

sealed interface MainActivityUiState {
    object Loading : MainActivityUiState
    data class Success(val userSettings: UserSettings) : MainActivityUiState
}