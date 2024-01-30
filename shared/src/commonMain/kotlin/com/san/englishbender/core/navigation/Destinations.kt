package com.san.englishbender.core.navigation

import com.san.englishbender.core.navigation.Screens.COLOR_PICKER_SCREEN
import com.san.englishbender.core.navigation.Screens.RECORDS_SCREEN
import com.san.englishbender.core.navigation.Screens.RECORD_DETAIL_SCREEN
import com.san.englishbender.core.navigation.Screens.STATS_SCREEN
import com.san.englishbender.core.navigation.Screens.TAG_ADD_EDIT_SCREEN
import com.san.englishbender.core.navigation.Screens.TAG_LIST_SCREEN

object Screens {
    const val STATS_SCREEN = "stats"
    const val RECORDS_SCREEN = "records"
    const val RECORD_DETAIL_SCREEN = "recordDetail"

    // ---
    const val TAG_LIST_SCREEN = "tag_list"
    const val TAG_ADD_EDIT_SCREEN = "tag_add_edit"
    const val COLOR_PICKER_SCREEN = "color_picker"
}

object DestinationsArgs {
    const val RECORD_ID_ARG = "recordId"
    const val TAG_ID_ARG = "tagId"
}

object Destinations {
    const val STATS_ROUTE = STATS_SCREEN
    const val RECORD_ROUTE = RECORDS_SCREEN
    const val RECORD_DETAIL_ROUTE = "$RECORD_DETAIL_SCREEN?recordId={recordId}"

    const val TAG_LIST_ROUTE = TAG_LIST_SCREEN
    const val TAG_CREATE_ROUTE = "$TAG_ADD_EDIT_SCREEN?tagId={tagId}"
    const val COLOR_PICKER_ROUTE = COLOR_PICKER_SCREEN
}