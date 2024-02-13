package com.san.englishbender.data.local.models

import com.san.englishbender.domain.entities.FlashCardEntity
import io.realm.kotlin.types.RealmObject

open class FlashCard : RealmObject {
    var id: String = ""
    var frontText: String = ""
    var backText: String = ""

    constructor(id: String, frontText: String, backText: String) {
        this.id = id
        this.frontText = frontText
        this.backText = backText
    }

    constructor() {}
}

fun FlashCard.toEntity() =
    FlashCardEntity(
        id = id,
        frontText = frontText,
        backText = backText
    )

fun FlashCardEntity.toLocal() =
    FlashCard(
        id = id,
        frontText = frontText,
        backText = backText
    )

fun List<FlashCard>.toEntity() = this.map { it.toEntity() }
fun List<FlashCardEntity>.toLocal() = this.map { it.toLocal() }