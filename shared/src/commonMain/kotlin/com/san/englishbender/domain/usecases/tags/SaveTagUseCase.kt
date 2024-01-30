package com.san.englishbender.domain.usecases.tags

import com.san.englishbender.data.local.models.Tag
import com.san.englishbender.domain.repositories.ITagsRepository


class SaveTagUseCase(private val tagsRepository: ITagsRepository) {
    suspend operator fun invoke(tag: Tag) = tagsRepository.saveTag(tag)
}