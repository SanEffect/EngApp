package com.san.englishbender.data.local.models

import com.san.englishbender.domain.entities.FlashCardEntity
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class FlashCard : RealmObject {
    @PrimaryKey
    var id: String = ""
    var word: String = ""
    var description: String = ""

    constructor(id: String, word: String, description: String) {
        this.id = id
        this.word = word
        this.description = description
    }

    constructor() {}
}

fun FlashCard.toEntity() =
    FlashCardEntity(
        id = id,
        word = word,
        description = description
    )

fun FlashCardEntity.toLocal() =
    FlashCard(
        id = id,
        word = word,
        description = description
    )

fun List<FlashCard>.toEntity() = this.map { it.toEntity() }
fun List<FlashCardEntity>.toLocal() = this.map { it.toLocal() }