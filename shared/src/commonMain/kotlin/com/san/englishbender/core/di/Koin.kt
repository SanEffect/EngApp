package com.san.englishbender.core.di

import com.san.englishbender.platformModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(enableNetworkLogs: Boolean = false, appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            commonModule(enableNetworkLogs = enableNetworkLogs),
            databaseModule,
            viewModelModule,
            useCaseModule,
            platformModule()
        )
    }

// called by iOS etc
fun initKoin() = initKoin(enableNetworkLogs = false) {}

fun commonModule(enableNetworkLogs: Boolean) = module {

//    single { createJson() }
//    single { createHttpClient(get(), get(), enableNetworkLogs = enableNetworkLogs) }
//    single { CoroutineScope(Dispatchers.Default + SupervisorJob() ) }
}