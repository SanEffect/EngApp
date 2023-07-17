package com.san.englishbender.domain.entities

import com.san.englishbender.CommonParcelable
import com.san.englishbender.CommonParcelize


@CommonParcelize
data class Label(
    var id: String = "",
    var name: String = "",
    var color: String = ""
) : CommonParcelable