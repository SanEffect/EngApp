package com.san.englishbender.domain.usecases.labels

import com.san.englishbender.domain.repositories.ILabelsRepository
import database.Label
import kotlinx.coroutines.flow.Flow

class GetAllLabelsUseCase(
    private val labelsRepository: ILabelsRepository
) {
    operator fun invoke(): Flow<List<Label>> = labelsRepository.getAllLabels()
}