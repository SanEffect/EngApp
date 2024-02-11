package com.san.englishbender.domain.entities

import androidx.compose.runtime.Immutable
import com.san.englishbender.CommonParcelable
import com.san.englishbender.CommonParcelize

@Immutable
@CommonParcelize
data class RecordEntity(
    var title: String = "",
    var text: String = "",
    var plainText: String = "",
    var id: String = "",
    var isDeleted: Boolean = false,
    var isDraft: Boolean = false,
    var creationDate: Long = 0,
    var backgroundColor: String = "",
    var tags: List<TagEntity>? = null
) : CommonParcelable

fun RecordEntity.isNotEqual(other: RecordEntity): Boolean {
    val isLabelsChanged = this.tags?.equals(other.tags) ?: false

    return !(this.title.trim() == other.title.trim() &&
            this.text.trim() == other.text.trim() &&
            this.backgroundColor == other.backgroundColor &&
            isLabelsChanged)
}