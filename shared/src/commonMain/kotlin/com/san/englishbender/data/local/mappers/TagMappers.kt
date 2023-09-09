package com.san.englishbender.data.local.mappers

import com.san.englishbender.data.local.models.Tag
import com.san.englishbender.domain.entities.TagEntity

fun Tag.toEntity() =
    TagEntity(
        id = id,
        name = name,
        color = color
    )

fun TagEntity.toLocal() =
    Tag(
        id = id,
        name = name,
        color = color
    )

fun List<Tag>.toEntity() = this.map { it.toEntity() }
fun List<TagEntity>.toLocal() = this.map { it.toLocal() }