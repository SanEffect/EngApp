package com.san.englishbender.data.repositories

import com.san.englishbender.data.Result
import com.san.englishbender.data.local.dataSources.ILabelsDataSource
import com.san.englishbender.data.local.mappers.toEntity
import com.san.englishbender.domain.entities.LabelEntity
import com.san.englishbender.domain.repositories.ILabelsRepository
import com.san.englishbender.ioDispatcher
import database.Label
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class LabelsRepository(
    private val labelsDataSource: ILabelsDataSource
) : ILabelsRepository {

    override fun getAllLabelsFlow(): Flow<List<LabelEntity>> =
        labelsDataSource.getAllLabelsFlow().map { list ->
            list.map { it.toEntity() }
        }

    override suspend fun getAllLabels(): List<LabelEntity> =
        labelsDataSource.getAllLabels().map { it.toEntity() }

    override suspend fun saveLabel(label: Label): Flow<Result<Unit>> = withContext(ioDispatcher) {
        return@withContext labelsDataSource.upsertLabel(label)
    }

    override suspend fun deleteLabel(labelId: String): Flow<Result<Unit>> =
        withContext(ioDispatcher) {
            return@withContext labelsDataSource.deleteLabel(labelId)
        }

}