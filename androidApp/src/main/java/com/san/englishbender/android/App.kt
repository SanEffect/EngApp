package com.san.englishbender.android

import android.app.Application
import com.san.englishbender.android.di.appModule
import com.san.englishbender.core.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level


class App : Application() {

    override fun onCreate() {
        super.onCreate()

//        if (BuildConfig.DEBUG) {
//            Timber.plant(Timber.DebugTree())
//        }

        initKoin {
            androidLogger()
            androidContext(this@App)
            modules(appModule)

            if (BuildConfig.DEBUG) {
                printLogger(Level.DEBUG)
            }
        }

//        startKoin {
//            androidLogger(Level.ERROR)
//            androidContext(this@App)
//            modules(
//                sharedModule,
//                appModule,
//                databaseModule,
//                viewModelModule,
//                useCasesModule
//            )
//            if (BuildConfig.DEBUG) {
//                printLogger(Level.DEBUG)
//            }
//        }
    }
}