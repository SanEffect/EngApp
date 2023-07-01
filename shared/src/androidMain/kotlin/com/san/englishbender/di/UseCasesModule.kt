package com.san.englishbender.di

import com.san.englishbender.domain.usecases.*
import org.koin.dsl.module

val useCasesModule = module {
    factory { GetRecordUseCase(get()) }
    factory { GetRecordsPagingUseCase(get()) }
    factory { GetRecordsUseCase(get()) }
    factory { SaveRecordUseCase(get()) }
    factory { RemoveRecordUseCase(get()) }
}