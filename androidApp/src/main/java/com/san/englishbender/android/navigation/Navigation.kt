package com.san.englishbender.android.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.san.englishbender.android.navigation.Destinations.RECORD_DETAIL_ROUTE
import com.san.englishbender.android.navigation.Destinations.STATS_ROUTE
import com.san.englishbender.android.navigation.DestinationsArgs.RECORD_ID_ARG
import com.san.englishbender.android.navigation.Screens.ADD_LABEL_SCREEN
import com.san.englishbender.android.navigation.Screens.COLOR_PICKER_SCREEN
import com.san.englishbender.android.navigation.Screens.CREATE_LABEL_SCREEN
import com.san.englishbender.android.navigation.Screens.RECORDS_SCREEN
import com.san.englishbender.android.navigation.Screens.RECORD_DETAIL_SCREEN
import com.san.englishbender.android.navigation.Screens.STATS_SCREEN

/**
 * Screens used in [TodoDestinations]
 */
object Screens {
    const val STATS_SCREEN = "stats"
    const val RECORDS_SCREEN = "records"
    const val RECORD_DETAIL_SCREEN = "recordDetail"

    // ---
    const val ADD_LABEL_SCREEN = "addLabel"
    const val CREATE_LABEL_SCREEN = "createLabel"
    const val COLOR_PICKER_SCREEN = "colorPicker"
}

/**
 * Arguments used in [TodoDestinations] routes
 */
object DestinationsArgs {
    const val USER_MESSAGE_ARG = "userMessage"
    const val RECORD_ID_ARG = "recordId"
    const val TITLE_ARG = "title"
}

/**
 * Destinations used in the [TodoActivity]
 */
object Destinations {
    const val STATS_ROUTE = STATS_SCREEN
    const val RECORD_ROUTE = RECORDS_SCREEN
//    const val RECORD_DETAIL_ROUTE = "$RECORD_DETAIL_SCREEN/{$RECORD_ID_ARG}"
//    const val RECORD_DETAIL_ROUTE = "recordDetail/{$RECORD_ID_ARG}"
//    const val RECORD_DETAIL_ROUTE = "recordDetail/{recordId}"
    const val RECORD_DETAIL_ROUTE = "$RECORD_DETAIL_SCREEN?recordId={recordId}"
//    const val RECORD_DETAIL_ROUTE = "recordDetail?recordId={$RECORD_ID_ARG}"

    const val LIST_LABELS_ROUTE = ADD_LABEL_SCREEN
    const val ADD_LABEL_ROUTE = CREATE_LABEL_SCREEN
    const val COLOR_PICKER_ROUTE = COLOR_PICKER_SCREEN
}

/**
 * Models the navigation actions in the app.
 */
class EBNavigationActions(private val navController: NavHostController) {

    fun navigateToStats() {
        navController.navigate(STATS_ROUTE)
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