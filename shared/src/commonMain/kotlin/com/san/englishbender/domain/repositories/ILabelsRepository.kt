package com.san.englishbender.domain.repositories

import com.san.englishbender.domain.entities.LabelEntity
import database.Label
import kotlinx.coroutines.flow.Flow

interface ILabelsRepository {
    fun getAllLabelsFlow() : Flow<List<LabelEntity>>
    suspend fun getAllLabels() : List<LabelEntity>

    suspend fun saveLabel(label: Label)

    suspend fun deleteLabel(labelId: String)
}