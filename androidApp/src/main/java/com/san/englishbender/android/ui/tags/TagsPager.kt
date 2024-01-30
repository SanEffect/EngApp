package com.san.englishbender.android.ui.tags

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import com.san.englishbender.android.ui.common.BaseDialogContent
import com.san.englishbender.domain.entities.TagEntity
import com.san.englishbender.ui.TagsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TagsPager(
    recordTags: List<TagEntity>,
    onTagClick: (List<TagEntity>) -> Unit,
    dismiss: () -> Unit
) {
    val tagsViewModel: TagsViewModel = getViewModel()
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { 3 })
    var tagId: String? = ""

    HorizontalPager(
        state = pagerState,
        userScrollEnabled = false
    ) { page ->

        val height = when (pagerState.currentPage) {
            2 -> 500.dp
            else -> 350.dp
        }

        BaseDialogContent(
            height = height,
            dismiss = dismiss
        ) {
            when (pagerState.currentPage) {
                0 -> TagsScreen(
                    tagsViewModel = tagsViewModel,
                    recordTags = recordTags,
                    addEditTag = { tag ->
                        tagId = tag?.id
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(1)
                        }
                    },
                    onTagClick = onTagClick,
                    dismiss = dismiss
                )

                1 -> AddEditTagScreen(
                    tagId = tagId,
                    tagsViewModel = tagsViewModel,
                    onColorPicker = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(2)
                        }
                    },
                    onBack = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(0)
                        }
                    }
                )

                2 -> ColorPickerScreen(
                    onSave = { hexCode ->
                        coroutineScope.launch {
                            tagsViewModel.saveTagColor(hexCode).join()
                            pagerState.animateScrollToPage(1)
                        }
                    },
                    onBack = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(1)
                        }
                    }
                )
            }
        }
    }
}