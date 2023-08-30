package com.san.englishbender.core.di

import com.san.englishbender.domain.usecases.labels.GetLabelsFlowUseCase
import com.san.englishbender.domain.usecases.labels.SaveLabelUseCase
import com.san.englishbender.domain.usecases.recordLabels.DeleteByRecordLabelIdUseCase
import com.san.englishbender.domain.usecases.recordLabels.DeleteRecordLabelByLabelIdUseCase
import com.san.englishbender.domain.usecases.recordLabels.DeleteRecordLabelByRecordIdUseCase
import com.san.englishbender.domain.usecases.records.GetRecordFlowUseCase
import com.san.englishbender.domain.usecases.stats.GetStatsUseCase
import com.san.englishbender.domain.usecases.records.GetRecordUseCase
import com.san.englishbender.domain.usecases.records.GetRecordWithLabelsUseCase
import com.san.englishbender.domain.usecases.records.GetRecordsCountUseCase
import com.san.englishbender.domain.usecases.records.GetRecordsUseCase
import com.san.englishbender.domain.usecases.records.RemoveRecordUseCase
import com.san.englishbender.domain.usecases.recordLabels.SaveRecordLabelUseCase
import com.san.englishbender.domain.usecases.records.SaveRecordUseCase
import com.san.englishbender.domain.usecases.stats.UpdateStatsUseCase
import org.koin.dsl.module

val useCaseModule = module {
    // --- Records
    single { GetRecordUseCase(get()) }
    single { GetRecordWithLabelsUseCase(get()) }
    single { GetRecordFlowUseCase(get()) }
    single { GetRecordsUseCase(get()) }
    single { SaveRecordUseCase(get()) }
    single { RemoveRecordUseCase(get(), get()) }
    single { GetRecordsCountUseCase(get()) }

    // --- RecordLabelCrossRef
    single { SaveRecordLabelUseCase(get()) }
    single { DeleteRecordLabelByRecordIdUseCase(get()) }
    single { DeleteRecordLabelByLabelIdUseCase(get()) }
    single { DeleteByRecordLabelIdUseCase(get()) }

    // --- Stats
    single { GetStatsUseCase(get()) }
    single { UpdateStatsUseCase(get(), get()) }

    // --- Labels
    single { GetLabelsFlowUseCase(get()) }
    single { SaveLabelUseCase(get()) }
}