package com.san.englishbender.domain.usecases.tags

import com.san.englishbender.domain.entities.TagEntity
import com.san.englishbender.domain.repositories.ITagsRepository


class SaveTagUseCase(private val tagsRepository: ITagsRepository) {
    suspend operator fun invoke(tag: TagEntity) = tagsRepository.saveTag(tag)
}