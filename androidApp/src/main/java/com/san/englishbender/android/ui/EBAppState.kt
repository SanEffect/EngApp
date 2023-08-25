package com.san.englishbender.android.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope


@Composable
fun rememberEBAppState(
    windowSizeClass: WindowSizeClass,
//    networkMonitor: NetworkMonitor,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): EBAppState {
//    NavigationTrackingSideEffect(navController)
    return remember(
        navController,
        coroutineScope,
        windowSizeClass,
//        networkMonitor,
    ) {
        EBAppState(
            navController,
            coroutineScope,
            windowSizeClass,
//            networkMonitor
        )
    }
}

class EBAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    val windowSizeClass: WindowSizeClass,
//    networkMonitor: NetworkMonitor,
//    userNewsResourceRepository: UserNewsResourceRepository,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination
//
//    fun navigateToTopLevelDestination(topLevelDestination: Destinations) {
////        trace("Navigation: ${topLevelDestination.name}") {
//            val topLevelNavOptions = navOptions {
//                // Pop up to the start destination of the graph to
//                // avoid building up a large stack of destinations
//                // on the back stack as users select items
//                popUpTo(navController.graph.findStartDestination().id) {
//                    saveState = true
//                }
//                // Avoid multiple copies of the same destination when
//                // reselecting the same item
//                launchSingleTop = true
//                // Restore state when reselecting a previously selected item
//                restoreState = true
//            }
//
//            when (topLevelDestination) {
//                Destinations.RECORD_ROUTE -> navController.navigate(Destinations.Records.route, topLevelNavOptions)
//                Destinations.RECORD_DETAIL_ROUTE -> navController.navigate(Destinations.RecordDetail.route, topLevelNavOptions)
////                INTERESTS -> navController.navigateToInterestsGraph(topLevelNavOptions)
//                else -> {}
//            }
////        }
//    }

//    val isOffline = networkMonitor.isOnline
//        .map(Boolean::not)
//        .stateIn(
//            scope = coroutineScope,
//            started = SharingStarted.WhileSubscribed(5_000),
//            initialValue = false,
//        )
}