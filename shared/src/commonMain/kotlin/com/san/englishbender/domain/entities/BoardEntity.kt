package com.san.englishbender.domain.entities

import com.san.englishbender.CommonParcelable
import com.san.englishbender.CommonParcelize

@CommonParcelize
data class BoardEntity(
    var id: String = "",
    var name: String = "",
    var description: String = "",
    var creationDate: Long = 0,
    var backgroundColor: String = "",
    var flashCards: List<FlashCardEntity>
) : CommonParcelable