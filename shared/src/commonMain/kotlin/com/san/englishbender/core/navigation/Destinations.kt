package com.san.englishbender.core.navigation

import com.san.englishbender.core.navigation.Screens.COLOR_PICKER_SCREEN
import com.san.englishbender.core.navigation.Screens.LABEL_CREATE_SCREEN
import com.san.englishbender.core.navigation.Screens.LABEL_LIST_SCREEN
import com.san.englishbender.core.navigation.Screens.RECORDS_SCREEN
import com.san.englishbender.core.navigation.Screens.RECORD_DETAIL_SCREEN
import com.san.englishbender.core.navigation.Screens.STATS_SCREEN

object Screens {
    const val STATS_SCREEN = "stats"
    const val RECORDS_SCREEN = "records"
    const val RECORD_DETAIL_SCREEN = "recordDetail"

    // ---
    const val LABEL_LIST_SCREEN = "label_list"
    const val LABEL_CREATE_SCREEN = "label_create"
    const val COLOR_PICKER_SCREEN = "color_picker"
}

object DestinationsArgs {
    const val RECORD_ID_ARG = "recordId"
    const val LABEL_ID_ARG = "labelId"
}

object Destinations {
    const val STATS_ROUTE = STATS_SCREEN
    const val RECORD_ROUTE = RECORDS_SCREEN
    const val RECORD_DETAIL_ROUTE = "$RECORD_DETAIL_SCREEN?recordId={recordId}"

    const val LABEL_LIST_ROUTE = LABEL_LIST_SCREEN
    const val LABEL_CREATE_ROUTE = "$LABEL_CREATE_SCREEN?labelId={labelId}"
    const val COLOR_PICKER_ROUTE = COLOR_PICKER_SCREEN
}