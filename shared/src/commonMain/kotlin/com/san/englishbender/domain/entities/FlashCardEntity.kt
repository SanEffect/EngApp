package com.san.englishbender.domain.entities

import com.san.englishbender.CommonParcelable
import com.san.englishbender.CommonParcelize

@CommonParcelize
data class FlashCardEntity(
    var id: String = "",
    var word: String = "",
    var description: String = ""
) : CommonParcelable