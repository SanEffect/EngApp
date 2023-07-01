package com.san.englishbender.ui.common.base

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class MviViewModel<STATE : BaseViewState<*>> : BaseViewModel() {

    private val _uiState = MutableStateFlow<BaseViewState<*>>(BaseViewState.Loading)
    val uiState = _uiState.asStateFlow()

    protected fun setState(state: STATE) = safeLaunch {
        _uiState.emit(state)
    }

    override fun startLoading() {
        super.startLoading()
        _uiState.value = BaseViewState.Loading
    }

    override fun handleError(exception: Throwable) {
        super.handleError(exception)
        _uiState.value = BaseViewState.Error(exception)
    }
}