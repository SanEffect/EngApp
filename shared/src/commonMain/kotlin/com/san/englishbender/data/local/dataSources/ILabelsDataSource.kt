package com.san.englishbender.data.local.dataSources

import database.Label
import kotlinx.coroutines.flow.Flow

interface ILabelsDataSource {
    suspend fun getAllLabels() : List<Label>
    fun getAllLabelsFlow() : Flow<List<Label>>

    suspend fun upsertLabel(label: Label)

    suspend fun deleteLabel(labelId: String)
}