package com.san.englishbender.android.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.tracing.trace
import com.san.englishbender.android.navigation.Destinations
import com.san.englishbender.android.navigation.DestinationsArgs
import com.san.englishbender.android.navigation.EBNavHost
import com.san.englishbender.android.navigation.TodoNavGraph
import com.san.englishbender.android.ui.recordDetails.RecordDetailScreen
import com.san.englishbender.android.ui.records.RecordsScreen


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EnglishBenderApp(
    windowSizeClass: WindowSizeClass,
    appState: EBAppState = rememberEBAppState(
//        networkMonitor = networkMonitor,
        windowSizeClass = windowSizeClass,
    ),
) {
//    val color = LocalBackgroundTheme.current.color
//    val tonalElevation = LocalBackgroundTheme.current.tonalElevation
    val color = Color.Transparent
    val tonalElevation = 0.dp

    val snackbarHostState = remember { SnackbarHostState() }

    Surface(
        color = if (color == Color.Unspecified) Color.Transparent else color,
        tonalElevation = if (tonalElevation == Dp.Unspecified) 0.dp else tonalElevation,
        modifier = Modifier.fillMaxSize(),
    ) {

        Scaffold(
            modifier = Modifier.semantics {
                testTagsAsResourceId = true
            },
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            snackbarHost = { SnackbarHost(snackbarHostState) },
            bottomBar = {
//                if (appState.shouldShowBottomBar) {
//                    NiaBottomBar(
//                        destinations = appState.topLevelDestinations,
//                        destinationsWithUnreadResources = unreadDestinations,
//                        onNavigateToDestination = appState::navigateToTopLevelDestination,
//                        currentDestination = appState.currentDestination,
//                        modifier = Modifier.testTag("NiaBottomBar"),
//                    )
//                }
            },
        ) { padding ->
            Column(Modifier.fillMaxSize().padding(padding)) {
                // Show the top app bar on top level destinations.
//                val destination = appState.currentTopLevelDestination
//                if (destination != null) {
//                    NiaTopAppBar(
//                        titleRes = destination.titleTextId,
//                        navigationIcon = NiaIcons.Search,
//                        navigationIconContentDescription = stringResource(
//                            id = settingsR.string.top_app_bar_navigation_icon_description,
//                        ),
//                        actionIcon = NiaIcons.Settings,
//                        actionIconContentDescription = stringResource(
//                            id = settingsR.string.top_app_bar_action_icon_description,
//                        ),
//                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
//                            containerColor = Color.Transparent,
//                        ),
//                        onActionClick = { showSettingsDialog = true },
//                        onNavigationClick = { appState.navigateToSearch() },
//                    )
//                }

                EBNavHost(appState = appState, onShowSnackbar = { message, action ->
                    snackbarHostState.showSnackbar(
                        message = message,
                        actionLabel = action,
                        duration = SnackbarDuration.Short,
                    ) == SnackbarResult.ActionPerformed
                })
            }
        }
    }
}