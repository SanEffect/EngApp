package com.san.englishbender.domain.usecases.labels

import com.san.englishbender.domain.repositories.ILabelsRepository
import database.Label


class SaveLabelUseCase(
    private val labelRepository: ILabelsRepository
) {
    suspend operator fun invoke(label: Label) = labelRepository.saveLabel(label)
}