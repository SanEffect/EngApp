package com.san.englishbender.ui.recordDetails

import com.san.englishbender.domain.entities.RecordEntity

data class RecordDetailState(
    val record: RecordEntity? = null,
    val originalText: String = "",
    val translatedText: String = "",
    val showTranslatedText: Boolean = false
)