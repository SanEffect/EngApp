package com.san.englishbender.data.local.models

import com.san.englishbender.domain.entities.FlashCardEntity
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class FlashCard : RealmObject {
    @PrimaryKey
    var id: String = ""
    var frontText: String = ""
    var backText: String = ""
    var isArchived: Boolean = false

    constructor(id: String, frontText: String, backText: String, isArchived: Boolean = false) {
        this.id = id
        this.frontText = frontText
        this.backText = backText
        this.isArchived = isArchived
    }

    constructor() {}
}

fun FlashCard.toEntity() =
    FlashCardEntity(
        id = id,
        frontText = frontText,
        backText = backText,
        isArchived = isArchived
    )

fun FlashCardEntity.toLocal() =
    FlashCard(
        id = id,
        frontText = frontText,
        backText = backText,
        isArchived = isArchived
    )

fun List<FlashCard>.toEntity() = this.map { it.toEntity() }
fun List<FlashCardEntity>.toLocal() = this.map { it.toLocal() }