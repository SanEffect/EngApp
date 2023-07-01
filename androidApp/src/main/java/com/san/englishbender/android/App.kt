package com.san.englishbender.android

import android.app.Application
import com.san.englishbender.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(
                sharedModule,
                appModule,
                databaseModule,
                viewModelModule,
                useCasesModule
            )
            if (BuildConfig.DEBUG) {
                printLogger(Level.DEBUG)
            }
        }
    }
}