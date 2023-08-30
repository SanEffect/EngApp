package com.san.englishbender.data.local.mappers

import com.san.englishbender.domain.entities.RecordEntity
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

fun RecordEntity.toLocal() : Record =
    Record(
        id = id,
        title = title,
        description = description,
        creationDate = creationDate,
        isDeleted = isDeleted,
        isDraft = isDraft,
        backgroundColor = backgroundColor
    )

fun List<Record>.toEntity() = this.map { it.toEntity() }
fun List<RecordEntity>.toLocal() = this.map { it.toLocal() }

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