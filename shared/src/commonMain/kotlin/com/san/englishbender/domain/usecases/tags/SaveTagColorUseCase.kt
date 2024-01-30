package com.san.englishbender.domain.usecases.tags

import com.san.englishbender.domain.repositories.ITagsRepository

class SaveTagColorUseCase(private val tagsRepository: ITagsRepository) {
    suspend operator fun invoke(hexCode: String) = tagsRepository.saveTagColor(hexCode)
}