package com.san.englishbender.data.local.models

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