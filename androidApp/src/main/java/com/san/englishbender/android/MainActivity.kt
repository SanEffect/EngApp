package com.san.englishbender.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.san.englishbender.android.core.workers.DatabaseWorker
import com.san.englishbender.android.ui.EnglishBenderApp
import com.san.englishbender.android.ui.MainActivityViewModel
import com.san.englishbender.android.ui.theme.EnglishBenderTheme
import com.san.englishbender.data.local.dataStore.IDataStore
import com.san.englishbender.data.local.dataStore.models.UserSettings
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.github.aakira.napier.log
import io.realm.kotlin.ext.realmListOf
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel


class MainActivity : ComponentActivity() {

    private val dataStore: IDataStore by inject()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        Napier.base(DebugAntilog())

        prePopulateDatabase()

        val viewModel = getViewModel<MainActivityViewModel>()
//        var uiState: MainActivityUiState by mutableStateOf(Loading)

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

        setContent {
//            val systemUiController = rememberSystemUiController()
//            val darkTheme = shouldUseDarkTheme(uiState)

            EnglishBenderTheme {
                EnglishBenderApp(
                    windowSizeClass = calculateWindowSizeClass(this)
                )
            }
        }
    }

    private fun prePopulateDatabase() {
        val appSettings = dataStore.getAppSettings()
        if (appSettings.isFirstLaunch) {
            val request = OneTimeWorkRequestBuilder<DatabaseWorker>().build()
            WorkManager.getInstance(this).enqueue(request)

            appSettings.isFirstLaunch = false
            dataStore.saveAppSettings(appSettings)
        }
    }
}
