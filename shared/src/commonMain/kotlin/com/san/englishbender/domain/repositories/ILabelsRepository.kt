package com.san.englishbender.domain.repositories

import database.Label
import com.san.englishbender.data.Result
import kotlinx.coroutines.flow.Flow

interface ILabelsRepository {
    fun getAllLabels() : Flow<List<Label>>

    suspend fun saveLabel(label: Label) : Flow<Result<Unit>>

    suspend fun deleteLabel(labelId: String) : Flow<Result<Unit>>
}