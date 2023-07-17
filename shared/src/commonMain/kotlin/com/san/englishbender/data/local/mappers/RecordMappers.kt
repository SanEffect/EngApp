package com.san.englishbender.data.local.mappers

import com.san.englishbender.domain.entities.Record
import database.RecordData

fun RecordData.toEntity() : Record =
    Record(
        id = id,
        title = title,
        description = description,
        creationDate = creationDate,
        isDeleted = isDeleted,
        isDraft = isDraft,
        backgroundColor = backgroundColor
    )

fun Record.toData() : RecordData =
    RecordData(
        id = id,
        title = title,
        description = description,
        creationDate = creationDate,
        isDeleted = isDeleted,
        isDraft = isDraft,
        backgroundColor = backgroundColor
    )