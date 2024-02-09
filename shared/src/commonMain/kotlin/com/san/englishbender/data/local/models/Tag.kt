package com.san.englishbender.data.local.models

import com.san.englishbender.domain.entities.TagEntity
import io.realm.kotlin.ext.backlinks
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class Tag : RealmObject {
    @PrimaryKey
    var id: String = ""
    var name: String = ""
    var color: String = ""
    var isWhite: Boolean = false
    val records: RealmResults<Record> by backlinks(Record::tags)

    constructor(id: String, name: String, color: String, isWhite: Boolean) {
        this.id = id
        this.name = name
        this.color = color
        this.isWhite = isWhite
    }

    constructor() {}
}

fun Tag.toEntity() =
    TagEntity(
        id = id,
        name = name,
        color = color,
        isWhite = isWhite
    )

fun TagEntity.toLocal() =
    Tag(
        id = id,
        name = name,
        color = color,
        isWhite = isWhite
    )

fun List<Tag>.toEntity() = this.map { it.toEntity() }
fun List<TagEntity>.toLocal() = this.map { it.toLocal() }