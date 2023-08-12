package com.san.englishbender.android.ui.labels


import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.san.englishbender.android.core.extensions.enterTransitionLeft
import com.san.englishbender.android.core.extensions.enterTransitionRight
import com.san.englishbender.android.core.extensions.exitTransitionLeft
import com.san.englishbender.android.core.extensions.exitTransitionRight
import com.san.englishbender.android.navigation.Destinations
import com.san.englishbender.android.navigation.DestinationsArgs
import com.san.englishbender.android.navigation.Screens
import com.san.englishbender.android.ui.common.BaseDialogContent
import com.san.englishbender.ui.LabelsViewModel
import database.Label
import org.koin.androidx.compose.getViewModel


@Composable
fun LabelsDialog(
    labels: List<Label>,
    onSaveLabel: (label: Label) -> Unit,
    onLabelSelected: (labels: List<Label>) -> Unit,
    dismiss: () -> Unit
) {
    val labelsViewModel: LabelsViewModel = getViewModel()
//    var selectedLabels: List<RecordLabelUI> by remember { mutableListOf(listOf()) }

    val navController = rememberNavController()

    navController.previousBackStackEntry?.destination

    NavHost(
        navController = navController,
        startDestination = Destinations.LABEL_LIST_ROUTE
    ) {
        composable(
            route = Destinations.LABEL_LIST_ROUTE,
            enterTransition = enterTransitionRight(),
            exitTransition = exitTransitionLeft()
        ) {
            ListLabelScreen(
                labelsViewModel = labelsViewModel,
                createLabel = { labelId ->
                    var arg = Screens.LABEL_CREATE_SCREEN
                    if (labelId != null) {
                        arg += "?labelId=$labelId"
                    }
                    navController.navigate(arg)
                },
                dismiss = dismiss
            )
        }

        composable(
            route = Destinations.LABEL_CREATE_ROUTE,
            enterTransition = enterTransitionLeft(),
            exitTransition = exitTransitionRight(),
            arguments = listOf(
                navArgument(DestinationsArgs.LABEL_ID_ARG) {
                    nullable = true
                    defaultValue = null
                    type = NavType.StringType
                },
            ),
        ) { entry ->
            val labelId = entry.arguments?.getString(DestinationsArgs.LABEL_ID_ARG)

            AddLabelScreen(
                labelId = labelId,
                labelsViewModel = labelsViewModel,
                onColorPicker = { navController.navigate(Destinations.COLOR_PICKER_ROUTE) },
                onBack = { navController.navigate(Destinations.LABEL_LIST_ROUTE) },
                dismiss = dismiss
            )
        }

        composable(
            route = Destinations.COLOR_PICKER_ROUTE,
            enterTransition = enterTransitionLeft(),
            exitTransition = exitTransitionRight()
        ) {
            ColorPickerScreen(
                onSave = {},
                onBack = {

//                        val navOptions = NavOptions.Builder()
//                            .setEnterAnim(enterTransitionLeft()) // Animation when navigating to this destination
//                            .setExitAnim(R.anim.slide_out_left) // Animation when navigating from this destination
//                            .setPopEnterAnim(R.anim.slide_in_left) // Animation when navigating back to this destination
//                            .setPopExitAnim(R.anim.slide_out_right) // Animation when navigating away from this destination
//                            .build()

                    navController.popBackStack()
                },
                dismiss = dismiss
            )
        }
    }

}
