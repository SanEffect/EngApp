package com.san.englishbender.di

import com.san.englishbender.ui.recordDetails.RecordDetailViewModel
import com.san.englishbender.ui.records.RecordsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { RecordsViewModel(get(), get()) }
//    single { RecordDetailViewModel(get(), get()) }
    viewModel { RecordDetailViewModel(get(), get()) }
}
