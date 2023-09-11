package com.san.englishbender.data.local.mappers

import com.san.englishbender.data.local.models.Record
import com.san.englishbender.data.local.models.RecordTagRef
import com.san.englishbender.domain.entities.RecordEntity
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.ext.toRealmList

fun Record.toEntity(): RecordEntity =
    RecordEntity(
        id = id,
        title = title,
        description = description,
        creationDate = creationDate,
        isDeleted = isDeleted,
        isDraft = isDraft,
        backgroundColor = backgroundColor,
        tags = tags.map { it.tagId }
    )

fun RecordEntity.toLocal(): Record =
    Record(
        id = id,
        title = title,
        description = description,
        creationDate = creationDate,
        isDeleted = isDeleted,
        isDraft = isDraft,
        backgroundColor = backgroundColor,
        tags = tags?.map {
            RecordTagRef(recordId = id, tagId = it)
        }?.toRealmList() ?: realmListOf()
    )

fun List<Record>.toEntity() = this.map { it.toEntity() }
fun List<RecordEntity>.toLocal() = this.map { it.toLocal() }
