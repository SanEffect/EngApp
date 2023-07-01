package com.san.englishbender.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import com.san.englishbender.android.ui.NavGraphs
import com.san.englishbender.android.ui.destinations.RecordDetailScreenDestination
import com.san.englishbender.android.ui.destinations.RecordsScreenDestination
import com.san.englishbender.android.ui.recordDetails.RecordDetailScreen
import com.san.englishbender.android.ui.records.RecordsScreen

enum class Screen(val route: String) {
    Home("home"),
    Records("records"),
    RecordDetail("recordDetail"),
    Profile("profile"),
    Settings("settings")
}


//class RootNode(
//    buildContext: BuildContext,
//    private val backStack: BackStack<NavTarget> = BackStack(
//        initialElement = NavTarget.Records,
//        savedStateMap = buildContext.savedStateMap
//    )
//) : ParentNode<RootNode.NavTarget>(
//    buildContext = buildContext,
//    navModel = backStack
//) {
//
//    sealed class NavTarget {
//        object Records : NavTarget()
//        object RecordDetail : NavTarget()
//    }
//
//    override fun resolve(navTarget: NavTarget, buildContext: BuildContext) =
//        when (navTarget) {
//            is NavTarget.Records -> RecordsNode(buildContext, backStack)
//
//            is NavTarget.RecordDetail -> node(buildContext) {
//                RecordDetailNode(buildContext) { backStack.push(NavTarget.Records) }
////                Text(
////                    text = "Placeholder for RecordDetail",
////                    color = MaterialTheme.colors.onBackground
////                )
//            }
//        }
//
//    @Composable
//    override fun View(modifier: Modifier) {
//
//        Children(
//            navModel = backStack,
//            transitionHandler = rememberBackstackFader(),
//            modifier = Modifier.fillMaxSize()
//        )
//
//        Button(onClick = {
//            backStack.push(NavTarget.Records)
//        }) {
//            Text("Go to Records")
//        }
//    }
//}

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EnglishBenderTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    DestinationsNavHost(navGraph = NavGraphs.root) {
//                        composable(RecordsScreenDestination) {
//                            RecordsScreen(navigator = destinationsNavigator)
//                        }
                    }

//                    val navController = rememberNavController()
//                    val coroutineScope = rememberCoroutineScope()

//                    NavHost(navController, startDestination = Screen.Records.route) {
////                        composable(Screen.Home.route) { HomeScreen() }
//                        composable(Screen.Records.route) {
//                            RecordsScreen(navController)
//                        }
//                        composable(Screen.RecordDetail.route) {
//                            RecordDetailScreen(
//                                navController,
//                                null
//                            )
//                        }
//                        composable(
//                            Screen.RecordDetail.route + "/{recordId}",
//                            arguments = listOf(navArgument("recordId") {
//                                type = NavType.StringType
//                            })
//                        ) { backStackEntry ->
//                            val recordId = backStackEntry.arguments?.getString("recordId")
//                            RecordDetailScreen(navController, recordId)
////                            RecordDetailScreen(itemId = itemId ?: "", navController = navController)
//                        }
////                        composable(Screen.RecordDetail.route) { RecordDetailScreen() }
////                        composable(Screen.Profile.route) { ProfileScreen() }
////                        composable(Screen.Settings.route) { SettingsScreen() }
//                    }
                }
            }
        }
    }
}

@Composable
fun GreetingView(text: String) {
    Text(text = text)
}

@Preview
@Composable
fun DefaultPreview() {
    EnglishBenderTheme {
        GreetingView("Hello, Android!")
    }
}
