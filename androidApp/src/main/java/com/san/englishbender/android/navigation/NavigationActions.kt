package com.san.englishbender.android.navigation

import androidx.navigation.NavHostController
import com.san.englishbender.core.navigation.Destinations.FLASHCARDS_ROUTE
import com.san.englishbender.core.navigation.Destinations.RECORD_DETAIL_ROUTE
import com.san.englishbender.core.navigation.Destinations.STATS_ROUTE
import com.san.englishbender.core.navigation.Screens.RECORDS_SCREEN

/**
 * Models the navigation actions in the app.
 */
class EBNavigationActions(private val navController: NavHostController) {

    fun navigateToStats() {
        navController.navigate(STATS_ROUTE)
    }

    fun navigateToFlashCards() {
        navController.navigate(FLASHCARDS_ROUTE)
    }

    fun navigateToRecords() {
        navController.navigate(RECORDS_SCREEN)
//        navController.navigate(RECORDS_SCREEN) {
//            popUpTo(navController.graph.findStartDestination().id) {
//                inclusive = true
//                saveState = true
//            }
//            launchSingleTop = true
//            restoreState = true
//        }
    }

//    fun navigateToRecordDetail(recordId: String) {
    fun navigateToRecordDetail() {
        navController.navigate(RECORD_DETAIL_ROUTE)
//        navController.navigate("$RECORD_DETAIL_SCREEN/$recordId")
    }

//    fun navigateToAddEditTask(title: Int, taskId: String?) {
//        navController.navigate(
//            "$ADD_EDIT_TASK_SCREEN/$title".let {
//                if (taskId != null) "$it?$TASK_ID_ARG=$taskId" else it
//            }
//        )
//    }
}