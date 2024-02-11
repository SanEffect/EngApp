package com.san.englishbender.domain.entities

import com.san.englishbender.CommonParcelable
import com.san.englishbender.CommonParcelize
import com.san.englishbender.randomUUID

@CommonParcelize
data class BoardEntity(
    var id: String = randomUUID(),
    var name: String = "",
    var backgroundColor: String = "",
    var flashCards: List<FlashCardEntity> = emptyList()
) : CommonParcelable