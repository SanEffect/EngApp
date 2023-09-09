package com.san.englishbender.domain.usecases.tags

import com.san.englishbender.domain.entities.TagEntity
import com.san.englishbender.domain.repositories.ITagsRepository

class GetTagsUseCase(
    private val labelsRepository: ITagsRepository
) {
    suspend operator fun invoke(): List<TagEntity> = labelsRepository.getAllTags()
}