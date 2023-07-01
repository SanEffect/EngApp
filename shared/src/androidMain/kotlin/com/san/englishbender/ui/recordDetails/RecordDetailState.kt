package com.san.englishbender.ui.recordDetails

import com.san.englishbender.domain.entities.Record

data class RecordDetailState(
    val record: Record? = null,
    val originalText: String = "",
    val translatedText: String = "",
    val showTranslatedText: Boolean = false
)