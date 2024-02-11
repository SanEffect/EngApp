package com.san.englishbender.core.di

import com.san.englishbender.domain.usecases.flashCards.GetBoardsFlowUseCase
import com.san.englishbender.domain.usecases.flashCards.GetBoardByIdUseCase
import com.san.englishbender.domain.usecases.flashCards.SaveBoardUseCase
import com.san.englishbender.domain.usecases.records.GetRecordFlowUseCase
import com.san.englishbender.domain.usecases.records.GetRecordsCountUseCase
import com.san.englishbender.domain.usecases.records.GetRecordsUseCase
import com.san.englishbender.domain.usecases.records.RemoveRecordUseCase
import com.san.englishbender.domain.usecases.records.SaveRecordUseCase
import com.san.englishbender.domain.usecases.stats.GetStatsFlowUseCase
import com.san.englishbender.domain.usecases.stats.GetStatsUseCase
import com.san.englishbender.domain.usecases.stats.UpdateStatsUseCase
import com.san.englishbender.domain.usecases.tags.DeleteTagUseCase
import com.san.englishbender.domain.usecases.tags.GetTagsFlowUseCase
import com.san.englishbender.domain.usecases.tags.SaveTagColorUseCase
import com.san.englishbender.domain.usecases.tags.SaveTagUseCase
import org.koin.dsl.module

val useCaseModule = module {
    // --- Records
//    single { GetRecordUseCase(get()) }
//    single { GetRecordWithLabelsUseCase(get()) }
    single { GetRecordFlowUseCase(get()) }
    single { GetRecordsUseCase(get()) }
    single { SaveRecordUseCase(get()) }
    single { RemoveRecordUseCase(get(), get()) }
    single { GetRecordsCountUseCase(get()) }

    // --- Stats
    single { GetStatsUseCase(get()) }
    single { GetStatsFlowUseCase(get()) }
    single { UpdateStatsUseCase(get(), get()) }

    // --- Tags
    single { GetTagsFlowUseCase(get()) }
    single { SaveTagUseCase(get()) }
    single { SaveTagColorUseCase(get()) }
    single { DeleteTagUseCase(get()) }

    // --- FlashCards
    single { GetBoardsFlowUseCase(get()) }
    single { GetBoardByIdUseCase(get()) }
    single { SaveBoardUseCase(get()) }
}