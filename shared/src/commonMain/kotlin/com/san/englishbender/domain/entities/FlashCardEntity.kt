package com.san.englishbender.domain.entities

import com.san.englishbender.CommonParcelable
import com.san.englishbender.CommonParcelize
import com.san.englishbender.randomUUID

@CommonParcelize
data class FlashCardEntity(
    var id: String = randomUUID(),
    var front: String = "",
    var back: String = "",
    var description: String = ""
) : CommonParcelable