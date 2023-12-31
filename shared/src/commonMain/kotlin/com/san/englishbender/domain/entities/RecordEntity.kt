package com.san.englishbender.domain.entities

import com.san.englishbender.CommonParcelable
import com.san.englishbender.CommonParcelize

@CommonParcelize
data class RecordEntity(
    var title: String = "",
    var description: String = "",
    var id: String = "",
    var isDeleted: Boolean = false,
    var isDraft: Boolean = false,
    var creationDate: Long = 0,
    var backgroundColor: String = "",
    var labels: List<String>? = null
) : CommonParcelable