package com.san.englishbender.data.local.mappers

import com.san.englishbender.domain.entities.RecordEntity
import database.Label
import database.Record
import database.SelectRecordWithLabels

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

fun SelectRecordWithLabels.toEntity() : RecordEntity =
    RecordEntity(
        id = id,
        title = title,
        description = description,
        creationDate = creationDate,
        isDeleted = isDeleted,
        isDraft = isDraft,
        backgroundColor = backgroundColor,
        labels = when (labelIds.isNotEmpty()) {
            true -> labelIds.split(",")
            false -> emptyList()
        }
    )