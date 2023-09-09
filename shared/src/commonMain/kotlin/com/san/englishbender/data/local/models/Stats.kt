package com.san.englishbender.data.local.models

import io.realm.kotlin.types.RealmObject

class Stats : RealmObject {
    var recordsCount: Long = 0
    var wordsCount: Long = 0
    var lettersCount: Long = 0

    constructor(recordsCount: Long, wordsCount: Long, lettersCount: Long) {
        this.recordsCount = recordsCount
        this.wordsCount = wordsCount
        this.lettersCount = lettersCount
    }

    constructor() {}
}