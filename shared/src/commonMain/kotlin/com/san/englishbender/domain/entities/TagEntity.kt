package com.san.englishbender.domain.entities

import com.san.englishbender.CommonParcelable
import com.san.englishbender.CommonParcelize


@CommonParcelize
data class TagEntity(
    var id: String = "",
    var name: String = "",
    var color: String = "",
    var isWhite: Boolean = false
) : CommonParcelable