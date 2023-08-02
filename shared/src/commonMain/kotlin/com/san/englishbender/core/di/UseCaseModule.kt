package com.san.englishbender.core.di

import com.san.englishbender.domain.usecases.labels.GetAllLabelsUseCase
import com.san.englishbender.domain.usecases.labels.SaveLabelUseCase
import com.san.englishbender.domain.usecases.records.GetRecordFlowUseCase
import com.san.englishbender.domain.usecases.stats.GetStatsUseCase
import com.san.englishbender.domain.usecases.records.GetRecordUseCase
import com.san.englishbender.domain.usecases.records.GetRecordsCountUseCase
import com.san.englishbender.domain.usecases.records.GetRecordsUseCase
import com.san.englishbender.domain.usecases.records.RemoveRecordUseCase
import com.san.englishbender.domain.usecases.records.SaveRecordUseCase
import com.san.englishbender.domain.usecases.stats.UpdateStatsUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetRecordUseCase(get()) }
    single { GetRecordFlowUseCase(get()) }
    single { GetRecordsUseCase(get()) }
    single { SaveRecordUseCase(get()) }
    single { RemoveRecordUseCase(get()) }
    single { GetRecordsCountUseCase(get()) }
    single { GetStatsUseCase(get()) }
    single { UpdateStatsUseCase(get(), get()) }
    single { GetAllLabelsUseCase(get()) }
    single { SaveLabelUseCase(get()) }
}