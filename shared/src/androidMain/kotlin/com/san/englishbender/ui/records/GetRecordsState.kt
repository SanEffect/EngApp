package com.san.englishbender.ui.records

import androidx.paging.PagingData
import com.san.englishbender.domain.entities.Record
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class GetRecordsState(
    val pagedData: Flow<PagingData<Record>> = emptyFlow(),
    val records: List<Record>? = null
)