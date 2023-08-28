package com.san.englishbender.core.navigation

sealed class NavigationCommand {

    class Push(val route: String) : NavigationCommand()

    class Pop(val route: String? = null) : NavigationCommand()
}