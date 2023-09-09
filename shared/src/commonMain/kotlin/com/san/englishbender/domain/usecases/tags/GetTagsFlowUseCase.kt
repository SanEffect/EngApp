package com.san.englishbender.domain.usecases.tags

import com.san.englishbender.domain.entities.TagEntity
import com.san.englishbender.domain.repositories.ITagsRepository
import kotlinx.coroutines.flow.Flow

class GetTagsFlowUseCase(
    private val labelsRepository: ITagsRepository
) {
    operator fun invoke(): Flow<List<TagEntity>> = labelsRepository.getAllTagsFlow()
}