package com.san.englishbender.data.local.dataSources

import com.san.englishbender.data.Result
import database.Label
import kotlinx.coroutines.flow.Flow

interface ILabelsDataSource {
    suspend fun getAllLabels() : List<Label>
    fun getAllLabelsFlow() : Flow<List<Label>>

    suspend fun upsertLabel(label: Label) : Result<Unit>

    suspend fun deleteLabel(labelId: String) : Result<Unit>
}