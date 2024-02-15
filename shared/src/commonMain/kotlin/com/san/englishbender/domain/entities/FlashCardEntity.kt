package com.san.englishbender.domain.entities

import com.san.englishbender.CommonParcelable
import com.san.englishbender.CommonParcelize
import com.san.englishbender.randomUUID
import kotlinx.serialization.Serializable

@Serializable
@CommonParcelize
data class FlashCardEntity(
    var id: String = randomUUID(),
    var frontText: String = "",
    var backText: String = "",
    var isArchived: Boolean = false
) : CommonParcelable