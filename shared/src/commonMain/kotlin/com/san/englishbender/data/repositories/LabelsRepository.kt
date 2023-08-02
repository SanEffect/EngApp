package com.san.englishbender.data.repositories

import com.san.englishbender.data.Result
import com.san.englishbender.data.local.dataSources.ILabelsDataSource
import com.san.englishbender.domain.repositories.ILabelsRepository
import com.san.englishbender.ioDispatcher
import database.Label
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class LabelsRepository(
    private val labelsDataSource: ILabelsDataSource
) : ILabelsRepository {

    override fun getAllLabels(): Flow<List<Label>> = labelsDataSource.getAllLabels()

    override suspend fun saveLabel(label: Label): Flow<Result<Unit>> = withContext(ioDispatcher) {
        return@withContext labelsDataSource.saveLabel(label)
    }

    override suspend fun deleteLabel(labelId: String): Flow<Result<Unit>> = withContext(ioDispatcher) {
        return@withContext labelsDataSource.deleteLabel(labelId)
    }

}