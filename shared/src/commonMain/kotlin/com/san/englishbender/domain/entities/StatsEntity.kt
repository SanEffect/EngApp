package com.san.englishbender.domain.entities

data class StatsEntity(
    var recordsCount: Long = 0L,
    var wordsCount: Long = 0L,
    var lettersCount: Long = 0L
)