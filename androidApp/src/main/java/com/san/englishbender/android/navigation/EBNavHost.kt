package com.san.englishbender.android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.tracing.trace
import com.san.englishbender.android.navigation.DestinationsArgs.RECORD_ID_ARG
import com.san.englishbender.android.ui.EBAppState
import com.san.englishbender.android.ui.recordDetails.RecordDetailScreen
import com.san.englishbender.android.ui.records.RecordsScreen
import timber.log.Timber


@Composable
fun EBNavHost(
    appState: EBAppState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    startDestination: String = Destinations.RECORD_ROUTE,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable(
//            route = Destinations.Records.route,
            route = Destinations.RECORD_ROUTE,
//            deepLinks = listOf(
//                navDeepLink { uriPattern = DEEP_LINK_URI_PATTERN },
//            ),
//            arguments = listOf(
//                navArgument(LINKED_NEWS_RESOURCE_ID) { type = NavType.StringType },
//            ),
        ) {
            Timber.tag("NavGraph").d("RecordsScreen NAV")
            RecordsScreen(
                onRecordClick = { recordId ->
                    trace("Navigation: ${Destinations.RECORD_DETAIL_ROUTE}") {
//                        appState.navigateToTopLevelDestination(Destinations.RecordDetail)
//                        navController.navigate(Destinations.RecordDetail.route)

//                        navController.navigate(Screens.RECORD_DETAIL_SCREEN + "/adoud-feds-u134dfdf")
//                        navController.navigate(Screens.RECORD_DETAIL_SCREEN)

                        var arg = Screens.RECORD_DETAIL_SCREEN
                        if (recordId != null) {
                            arg += "?recordId=$recordId"
                        }

//                        navController.navigate(Screens.RECORD_DETAIL_SCREEN + "?recordId=adoud-feds-u134dfdf")
                        navController.navigate(arg)
                    }
                }
            )
        }

        composable(
            route = Destinations.RECORD_DETAIL_ROUTE,
            arguments = listOf(
                navArgument(RECORD_ID_ARG) {
                    nullable = true
                    defaultValue = null
                    type = NavType.StringType
                },
            ),
        ) { entry ->
            val recordId = entry.arguments?.getString(RECORD_ID_ARG)

            Timber.tag("RecordDetailScreen").d("recordId: $recordId")

            RecordDetailScreen(
                onBackClick = {
                    trace("Navigation: ${Destinations.RECORD_ROUTE}") {
//                        appState.navigateToTopLevelDestination(Destinations.Records)
//                        navController.navigate(Destinations.Records.route)
                        navController.navigate(Destinations.RECORD_ROUTE)
                    }
                },
                recordId
            )
        }

//        composable(
//            Destinations.RecordDetail.route + "/{recordId}",
//            arguments = listOf(navArgument("recordId") {
//                type = NavType.StringType
//            })
//        ) { backStackEntry ->
//            val recordId = backStackEntry.arguments?.getString("recordId")
//            RecordDetailScreen(
//                onBackClick = {
//
//                },
//                recordId
//            )
//        }

    }
}