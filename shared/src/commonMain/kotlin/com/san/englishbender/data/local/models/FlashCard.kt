package com.san.englishbender.data.local.models

import com.san.englishbender.domain.entities.FlashCardEntity
import io.realm.kotlin.types.EmbeddedRealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class FlashCard : EmbeddedRealmObject {
    var id: String = ""
    var front: String = ""
    var back: String = ""
    var description: String = ""

    constructor(id: String, front: String, back: String, description: String) {
        this.id = id
        this.front = front
        this.back = back
        this.description = description
    }

    constructor() {}
}

fun FlashCard.toEntity() =
    FlashCardEntity(
        id = id,
        front = front,
        back = back,
        description = description
    )

fun FlashCardEntity.toLocal() =
    FlashCard(
        id = id,
        front = front,
        back = back,
        description = description
    )

fun List<FlashCard>.toEntity() = this.map { it.toEntity() }
fun List<FlashCardEntity>.toLocal() = this.map { it.toLocal() }