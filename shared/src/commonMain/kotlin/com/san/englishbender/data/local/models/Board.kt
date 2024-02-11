package com.san.englishbender.data.local.models

import com.san.englishbender.domain.entities.BoardEntity
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class Board : RealmObject {
    @PrimaryKey
    var id: String = ""
    var name: String = ""
    var backgroundColor: String = ""
    var flashCards: RealmList<FlashCard> = realmListOf()

    constructor(
        id: String,
        name: String,
        backgroundColor: String = "",
        flashCards: RealmList<FlashCard>
    ) {
        this.id = id
        this.name = name
        this.backgroundColor = backgroundColor
        this.flashCards = flashCards
    }

    constructor() {}
}

fun Board.toEntity() =
    BoardEntity(
        id = id,
        name = name,
        backgroundColor = backgroundColor,
        flashCards = flashCards.toEntity()
    )

fun BoardEntity.toLocal() =
    Board(
        id = id,
        name = name,
        backgroundColor = backgroundColor,
        flashCards = flashCards.toLocal().toRealmList()
    )

fun List<Board>.toEntity() = this.map { it.toEntity() }
fun List<BoardEntity>.toLocal() = this.map { it.toLocal() }