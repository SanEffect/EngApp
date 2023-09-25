package com.san.englishbender.data.local.mappers

import com.san.englishbender.data.local.models.Stats
import com.san.englishbender.domain.entities.StatsEntity


fun Stats.toEntity() =
    StatsEntity(
        recordsCount = this.recordsCount,
        wordsCount = this.wordsCount,
        lettersCount = this.lettersCount
    )

fun StatsEntity.toLocal() =
    Stats(this.recordsCount, this.wordsCount, this.lettersCount)