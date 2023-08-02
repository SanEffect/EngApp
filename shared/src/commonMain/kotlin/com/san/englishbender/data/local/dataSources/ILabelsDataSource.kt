package com.san.englishbender.data.local.dataSources

import com.san.englishbender.data.Result
import database.Label
import kotlinx.coroutines.flow.Flow

interface ILabelsDataSource {
    fun getAllLabels() : Flow<List<Label>>

    suspend fun saveLabel(label: Label) : Flow<Result<Unit>>

    suspend fun deleteLabel(labelId: String) : Flow<Result<Unit>>
}