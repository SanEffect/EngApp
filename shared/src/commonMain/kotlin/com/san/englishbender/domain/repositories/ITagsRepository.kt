package com.san.englishbender.domain.repositories

import com.san.englishbender.data.local.models.Tag
import com.san.englishbender.domain.entities.TagEntity
import kotlinx.coroutines.flow.Flow

interface ITagsRepository {
    fun getAllTagsFlow() : Flow<List<TagEntity>>
    suspend fun getAllTags() : List<TagEntity>
    suspend fun saveTag(tag: Tag)
    suspend fun saveTagColor(hexCode: String)
    suspend fun deleteTag(tagId: String)
}