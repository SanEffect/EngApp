package com.san.englishbender.data.local.models

import com.san.englishbender.domain.entities.RecordEntity
import com.san.englishbender.domain.entities.TagEntity
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class Record : RealmObject {
    @PrimaryKey
    var id: String = ""
    var title: String = ""
    var text: String = ""
    var plainText: String = ""
    var isDraft: Boolean = false
    var isDeleted: Boolean = false
    var creationDate: Long = 0
    var backgroundColor: String = ""
    var tags: RealmList<Tag> = realmListOf()

    constructor(
        id: String,
        title: String,
        text: String,
        plainText: String,
        isDraft: Boolean = false,
        isDeleted: Boolean = false,
        creationDate: Long = 0,
        backgroundColor: String = "",
        tags: RealmList<Tag>
    ) {
        this.id = id
        this.title = title
        this.text = text
        this.plainText = plainText
        this.isDraft = isDraft
        this.isDeleted = isDeleted
        this.creationDate = creationDate
        this.backgroundColor = backgroundColor
        this.tags = tags
    }

    constructor() {}
}

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