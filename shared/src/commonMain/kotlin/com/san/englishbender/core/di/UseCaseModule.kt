package com.san.englishbender.core.di

import com.san.englishbender.domain.usecases.GetRecordUseCase
import com.san.englishbender.domain.usecases.GetRecordsUseCase
import com.san.englishbender.domain.usecases.RemoveRecordUseCase
import com.san.englishbender.domain.usecases.SaveRecordUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetRecordUseCase(get()) }
    single { GetRecordsUseCase(get()) }
    single { SaveRecordUseCase(get()) }
    single { RemoveRecordUseCase(get()) }
}