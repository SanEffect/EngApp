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
//    var tags: List<String>? = null
    var tags: List<TagEntity>? = null
) : CommonParcelable

fun RecordEntity.isNotEqual(other: RecordEntity): Boolean {
    val isLabelsChanged = this.tags?.equals(other.tags) ?: false

    return (this.title.trim() != other.title.trim() ||
            this.description.trim() != other.description.trim() ||
            this.backgroundColor != other.backgroundColor ||
            isLabelsChanged)
}