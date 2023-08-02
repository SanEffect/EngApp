package com.san.englishbender.data.local.mappers

import com.san.englishbender.domain.entities.RecordEntity
import database.Record

fun Record.toEntity() : RecordEntity =
    RecordEntity(
        id = id,
        title = title,
        description = description,
        creationDate = creationDate,
        isDeleted = isDeleted,
        isDraft = isDraft,
        backgroundColor = backgroundColor
    )

fun RecordEntity.toData() : Record =
    Record(
        id = id,
        title = title,
        description = description,
        creationDate = creationDate,
        isDeleted = isDeleted,
        isDraft = isDraft,
        backgroundColor = backgroundColor
    )