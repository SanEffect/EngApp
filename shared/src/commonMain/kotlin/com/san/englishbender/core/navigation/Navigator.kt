package com.san.englishbender.core.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class Navigator {

    private val _destination: MutableSharedFlow<NavigationCommand> = MutableSharedFlow(1)
    val destination = _destination.asSharedFlow()

    fun navigateTo(route: String) {
        this._destination.tryEmit(NavigationCommand.Push(route))
    }

    fun popBackStack(route: String? = null) {
        this._destination.tryEmit(NavigationCommand.Pop(route))
    }
}