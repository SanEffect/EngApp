package com.san.englishbender.data.local.mappers

import com.san.englishbender.data.local.models.Record
import com.san.englishbender.data.local.models.Tag
import com.san.englishbender.domain.entities.RecordEntity
import com.san.englishbender.domain.entities.TagEntity
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.ext.toRealmList

fun Record.toEntity(): RecordEntity =
    RecordEntity(
        id = id,
        title = title,
        text = text,
        plainText = plainText,
        creationDate = creationDate,
        isDeleted = isDeleted,
        isDraft = isDraft,
        backgroundColor = backgroundColor,
        tags = tags.map {
            TagEntity(
                id = it.id,
                name = it.name,
                color = it.color
            )
        }
    )

fun RecordEntity.toLocal(): Record =
    Record(
        id = id,
        title = title,
        text = text,
        plainText = plainText,
        creationDate = creationDate,
        isDeleted = isDeleted,
        isDraft = isDraft,
        backgroundColor = backgroundColor,
        tags = tags?.map { Tag(it.id, it.name, it.color, it.isWhite) }?.toRealmList() ?: realmListOf()
    )

fun List<Record>.toEntity() = this.map { it.toEntity() }
fun List<RecordEntity>.toLocal() = this.map { it.toLocal() }
