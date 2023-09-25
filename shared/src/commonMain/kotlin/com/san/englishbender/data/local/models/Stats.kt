package com.san.englishbender.data.local.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class Stats : RealmObject {
    @PrimaryKey
    var id: String = ""
    var recordsCount: Long = 0L
    var wordsCount: Long = 0L
    var lettersCount: Long = 0L

    constructor(recordsCount: Long, wordsCount: Long, lettersCount: Long) {
        this.recordsCount = recordsCount
        this.wordsCount = wordsCount
        this.lettersCount = lettersCount
    }

    constructor() {}
}