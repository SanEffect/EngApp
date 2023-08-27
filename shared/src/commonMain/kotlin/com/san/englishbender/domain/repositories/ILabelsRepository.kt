package com.san.englishbender.domain.repositories

import database.Label
import com.san.englishbender.data.Result
import com.san.englishbender.domain.entities.LabelEntity
import kotlinx.coroutines.flow.Flow

interface ILabelsRepository {
    fun getAllLabelsFlow() : Flow<List<LabelEntity>>
    suspend fun getAllLabels() : List<LabelEntity>

    suspend fun saveLabel(label: Label) : Result<Unit>

    suspend fun deleteLabel(labelId: String) : Result<Unit>
}