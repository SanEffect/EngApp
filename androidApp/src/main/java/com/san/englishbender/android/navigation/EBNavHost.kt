package com.san.englishbender.android.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.san.englishbender.android.navigation.DestinationsArgs.RECORD_ID_ARG
import com.san.englishbender.android.ui.EBAppState
import com.san.englishbender.android.ui.common.AppDrawer
import com.san.englishbender.android.ui.recordDetails.RecordDetailScreen
import com.san.englishbender.android.ui.records.RecordsScreen
import com.san.englishbender.android.ui.stats.StatsScreen
import io.github.aakira.napier.log
import kotlinx.coroutines.launch


@Composable
fun EBNavHost(
    modifier: Modifier = Modifier,
    appState: EBAppState,
    navController: NavHostController = rememberNavController(),
    onShowSnackbar: suspend (String, String?) -> Boolean,
    startDestination: String = Destinations.RECORD_ROUTE,
    navActions: EBNavigationActions = remember(navController) {
        EBNavigationActions(navController)
    }
) {
//    val navController = appState.navController
    val coroutineScope = rememberCoroutineScope()
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: startDestination
    val drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable(
            route = Destinations.STATS_ROUTE,
        ) {
            log { "STATS_ROUTE" }
            AppDrawer(
                drawerState,
//                navController,
                currentRoute,
                navActions,
                content = {
                    StatsScreen(
                        openDrawer = { coroutineScope.launch { drawerState.open() } }
                    )
                }
            )
        }

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
            log { "RECORD_ROUTE" }
            AppDrawer(
                drawerState,
//                navController,
                currentRoute,
                navActions,
                content = {
                    RecordsScreen(
                        onRecordClick = { recordId ->
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
                        },
                        openDrawer = { coroutineScope.launch { drawerState.open() } }
                    )
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

            log { "RECORD_DETAIL_ROUTE" }

            RecordDetailScreen(
                onBackClick = {

//                        appState.navigateToTopLevelDestination(Destinations.Records)
//                        navController.navigate(Destinations.Records.route)
                    navController.navigate(Destinations.RECORD_ROUTE)
                },
                onRecordSaved = {},
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