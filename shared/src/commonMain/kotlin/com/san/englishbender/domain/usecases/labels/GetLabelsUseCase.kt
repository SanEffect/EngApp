package com.san.englishbender.domain.usecases.labels

import com.san.englishbender.domain.entities.LabelEntity
import com.san.englishbender.domain.repositories.ILabelsRepository

class GetLabelsUseCase(
    private val labelsRepository: ILabelsRepository
) {
    suspend operator fun invoke(): List<LabelEntity> = labelsRepository.getAllLabels()
}