package com.san.englishbender.domain.entities

import com.san.englishbender.CommonParcelable
import com.san.englishbender.CommonParcelize
import com.san.englishbender.randomUUID

@CommonParcelize
data class FlashCardEntity(
    var id: String = randomUUID(),
    var frontText: String = "",
    var backText: String = "",
) : CommonParcelable