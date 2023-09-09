package com.san.englishbender.android.ui.tags


import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.san.englishbender.android.core.extensions.enterTransitionLeft
import com.san.englishbender.android.core.extensions.enterTransitionRight
import com.san.englishbender.android.core.extensions.exitTransitionLeft
import com.san.englishbender.android.core.extensions.exitTransitionRight
import com.san.englishbender.core.navigation.Destinations
import com.san.englishbender.core.navigation.DestinationsArgs
import com.san.englishbender.core.navigation.Screens
import com.san.englishbender.domain.entities.TagEntity
import com.san.englishbender.ui.TagsViewModel
import org.koin.androidx.compose.getViewModel


@Composable
fun LabelsNavHost(
    labels: List<TagEntity>,
    recordLabels: List<TagEntity>,
    onLabelClick: (List<TagEntity>) -> Unit,
    dismiss: () -> Unit
) {
    val navController = rememberNavController()
    val tagsViewModel: TagsViewModel = getViewModel()

    var enterTransition = enterTransitionLeft()
    var exitTransition = exitTransitionRight()

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
                tagsViewModel = tagsViewModel,
                recordLabels = recordLabels,
                createLabel = { labelId ->
                    navController.navigate(
                        Screens.LABEL_CREATE_SCREEN.let {
                            if (labelId != null) "$it?labelId=$labelId" else it
                        }
                    )
                },
                onLabelClick = onLabelClick,
                dismiss = dismiss
            )
        }

        composable(
            route = Destinations.LABEL_CREATE_ROUTE,
//            enterTransition = enterTransitionLeft(),
//            exitTransition = exitTransitionRight(),
            enterTransition = enterTransition,
            exitTransition = exitTransition,
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
                tagsViewModel = tagsViewModel,
                onColorPicker = {

                    enterTransition = enterTransitionRight()
                    exitTransition = exitTransitionLeft()

//                    val navOptions = NavOptions.Builder()
//                        .setEnterAnim(R.anim.slide_out)
//                        .setExitAnim(R.anim.slide_in)
//                        .build()
//                    navController.navigate(Destinations.COLOR_PICKER_ROUTE, navOptions)
                    navController.navigate(Destinations.COLOR_PICKER_ROUTE)
                },
                onBack = {
                    enterTransition = enterTransitionLeft()
                    exitTransition = exitTransitionRight()
                    navController.navigate(Destinations.LABEL_LIST_ROUTE) },
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
