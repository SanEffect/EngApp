package com.san.englishbender.data.local.dataSources

import com.san.englishbender.data.Result
import com.san.englishbender.domain.entities.LabelEntity
import database.Label
import kotlinx.coroutines.flow.Flow

interface ILabelsDataSource {
    suspend fun getAllLabels() : List<Label>
    fun getAllLabelsFlow() : Flow<List<Label>>

    suspend fun upsertLabel(label: Label) : Flow<Result<Unit>>

    suspend fun deleteLabel(labelId: String) : Flow<Result<Unit>>
}