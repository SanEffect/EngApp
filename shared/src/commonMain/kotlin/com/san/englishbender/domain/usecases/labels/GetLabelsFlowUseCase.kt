package com.san.englishbender.domain.usecases.labels

import com.san.englishbender.domain.entities.LabelEntity
import com.san.englishbender.domain.repositories.ILabelsRepository
import kotlinx.coroutines.flow.Flow

class GetLabelsFlowUseCase(
    private val labelsRepository: ILabelsRepository
) {
    operator fun invoke(): Flow<List<LabelEntity>> = labelsRepository.getAllLabelsFlow()
}