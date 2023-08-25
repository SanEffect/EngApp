package com.san.englishbender.data.local.mappers

import com.san.englishbender.domain.entities.LabelEntity
import database.Label

fun Label.toEntity() =
    LabelEntity(
        id = id,
        name = name,
        color = color
    )

fun LabelEntity.toData() =
    Label(
        id = id,
        name = name,
        color = color
    )