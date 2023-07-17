package com.san.englishbender.android.di

import com.san.englishbender.android.ui.MainActivityViewModel
//import com.san.englishbender.data.DataStore
//import com.san.englishbender.domain.repositories.IUserDataRepository
//import com.san.englishbender.data.repositories.UserDataRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel { MainActivityViewModel() }

//    single { DataStore(get()) }
//    single<IUserDataRepository> { UserDataRepository(get()) }
}