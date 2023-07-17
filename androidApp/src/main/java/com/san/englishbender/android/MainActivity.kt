package com.san.englishbender.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.san.englishbender.android.navigation.TodoNavGraph
import com.san.englishbender.android.ui.EnglishBenderApp
//import com.san.englishbender.android.ui.EnglishBenderApp
import com.san.englishbender.android.ui.MainActivityUiState
import com.san.englishbender.android.ui.MainActivityUiState.Loading
import com.san.englishbender.android.ui.MainActivityUiState.Success
import com.san.englishbender.android.ui.MainActivityViewModel
import com.san.englishbender.android.ui.theme.EnglishBenderTheme
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.github.aakira.napier.log
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel
import timber.log.Timber


class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        val viewModel = getViewModel<MainActivityViewModel>()
//        var uiState: MainActivityUiState by mutableStateOf(Loading)

        Napier.base(DebugAntilog())

        // Update the uiState
//        lifecycleScope.launch {
//            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.uiState
//                    .onEach { uiState = it }
//                    .collect()
//            }
//        }

        // Keep the splash screen on-screen until the UI state is loaded. This condition is
        // evaluated each time the app needs to be redrawn so it should be fast to avoid blocking
        // the UI.
//        splashScreen.setKeepOnScreenCondition {
//            when (uiState) {
//                Loading -> true
//                is Success -> false
//            }
//        }

        Timber.tag("NavGraph").d("setContent")

        log { "NAPIER SUKA" }

        setContent {
//            val systemUiController = rememberSystemUiController()
//            val darkTheme = shouldUseDarkTheme(uiState)

            EnglishBenderTheme {
//                TodoNavGraph()

                EnglishBenderApp(
                    windowSizeClass = calculateWindowSizeClass(this)
                )

            }
        }
    }
}
