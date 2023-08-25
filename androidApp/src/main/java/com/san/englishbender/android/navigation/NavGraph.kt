package com.san.englishbender.android.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.rememberDrawerState
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
import com.san.englishbender.android.ui.recordDetails.RecordDetailScreen
import com.san.englishbender.android.ui.records.RecordsScreen
import io.github.aakira.napier.log
import kotlinx.coroutines.CoroutineScope


@Composable
fun TodoNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    startDestination: String = Destinations.RECORD_ROUTE,
    navActions: EBNavigationActions = remember(navController) {
        EBNavigationActions(navController)
    }
) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: startDestination

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(
            Destinations.RECORD_ROUTE,
//            arguments = listOf(
//                navArgument(USER_MESSAGE_ARG) { type = NavType.IntType; defaultValue = 0 }
//            )
        ) { entry ->
            log( tag = "NavGraph") { "nav RecordsScreen" }
            RecordsScreen(
                onRecordClick = { navActions.navigateToRecordDetail() },
                openDrawer = {}
            )
        }

        composable(
            route = Destinations.RECORD_DETAIL_ROUTE,
            arguments = listOf(
                navArgument(RECORD_ID_ARG) { type = NavType.StringType; nullable = true },
            ),
        ) { entry ->
            val recordId = entry.arguments?.getString(RECORD_ID_ARG)
            RecordDetailScreen(
                onBackClick = {
                    navActions.navigateToRecords()
                },
                onRecordSaved = {},
                null
            )
        }

//        composable(
//            route = Destinations.RECORD_DETAIL_ROUTE,
//            arguments = listOf(
//                navArgument(RECORD_ID_ARG) { type = NavType.StringType;  nullable = true },
//            ),
//        ) {
//            RecordDetailScreen(
//                onBackClick = { navActions.navigateToRecords() },
//                null
//            )
//        }
    }
}