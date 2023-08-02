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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.san.englishbender.android.core.extensions.enterTransitionLeft
import com.san.englishbender.android.core.extensions.enterTransitionRight
import com.san.englishbender.android.core.extensions.exitTransitionLeft
import com.san.englishbender.android.core.extensions.exitTransitionRight
import com.san.englishbender.android.navigation.Destinations
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



        NavHost(
            navController = navController,
            startDestination = Destinations.LIST_LABELS_ROUTE
        ) {
            composable(
                route = Destinations.LIST_LABELS_ROUTE,
                enterTransition = enterTransitionRight(),
                exitTransition = exitTransitionLeft()
            ) {
                ListLabelScreen(
                    labelsViewModel = labelsViewModel,
                    createLabel = {
                        navController.navigate(Destinations.ADD_LABEL_ROUTE)
                    },
                    dismiss = dismiss
                )
            }

            composable(
                route = Destinations.ADD_LABEL_ROUTE,
                enterTransition = enterTransitionLeft(),
                exitTransition = exitTransitionRight()
            ) {
                AddLabelScreen(
                    labelsViewModel = labelsViewModel,
                    onColorPicker = { navController.navigate(Destinations.COLOR_PICKER_ROUTE) },
                    onBack = { navController.navigate(Destinations.LIST_LABELS_ROUTE) }
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

                        navController.navigate(Destinations.ADD_LABEL_ROUTE)
                    }
                )
            }
        }

}
