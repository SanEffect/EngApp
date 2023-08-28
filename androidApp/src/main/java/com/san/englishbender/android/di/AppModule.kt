package com.san.englishbender.android.di

import com.san.englishbender.android.ui.MainActivityViewModel
import com.san.englishbender.data.local.dataStore.DataStoreRealm
import com.san.englishbender.data.repositories.UserDataRepository
import com.san.englishbender.domain.repositories.IUserDataRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel { MainActivityViewModel(get()) }

    single { DataStoreRealm(get()) }
    single<IUserDataRepository> { UserDataRepository(get()) }
}