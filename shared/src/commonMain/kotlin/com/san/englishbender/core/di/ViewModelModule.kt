package com.san.englishbender.core.di

import com.san.englishbender.ui.TagsViewModel
import com.san.englishbender.ui.recordDetail.RecordDetailViewModel
import com.san.englishbender.ui.records.RecordsViewModel
import com.san.englishbender.ui.stats.StatsViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { RecordsViewModel(get(), get(), get()) }
    single { RecordDetailViewModel(get(), get(), get(), get(), get()) }
    single { StatsViewModel(get(), get()) }
    single { TagsViewModel(get(), get(), get()) }
}