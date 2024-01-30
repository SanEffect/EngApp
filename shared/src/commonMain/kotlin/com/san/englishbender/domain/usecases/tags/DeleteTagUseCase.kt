package com.san.englishbender.domain.usecases.tags

import com.san.englishbender.domain.repositories.ITagsRepository

class DeleteTagUseCase(private val tagsRepository: ITagsRepository) {
    suspend operator fun invoke(tagId: String) = tagsRepository.deleteTag(tagId)
}