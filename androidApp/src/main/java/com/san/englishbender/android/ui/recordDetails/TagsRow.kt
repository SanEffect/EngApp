package com.san.englishbender.android.ui.recordDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.NewLabel
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.san.englishbender.android.core.extensions.toColor
import com.san.englishbender.android.ui.theme.ColorsPreset
import com.san.englishbender.domain.entities.TagEntity

@Composable
fun TagsRow(
    modifier: Modifier = Modifier,
    selectedTags: List<TagEntity>,
    onDeleteTagClick: (String) -> Unit,
    onMoreTagsClick: () -> Unit,
) {
    LazyRow(
        modifier = modifier
            .height(100.dp)
            .padding(
                start = 12.dp,
                end = 12.dp,
                top = 12.dp,
                bottom = 24.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        item {
            Icon(
                rememberVectorPainter(Icons.Outlined.NewLabel),
                contentDescription = null,
                modifier = Modifier.clickable { onMoreTagsClick() }
            )
        }
        items(selectedTags, key = { it.id }) { tag ->
            TagItem(
                tag = tag,
                containerColor = tag.color.toColor,
                onDeleteClick = { tagId -> onDeleteTagClick(tagId) }
            )
        }
    }
}

@Composable
fun TagItem(
    modifier: Modifier = Modifier,
    tag: TagEntity,
    containerColor: Color = Color.White,
    onDeleteClick: (String) -> Unit,
) {
    Row(
        modifier = modifier
            .border(1.dp, Color.DarkGray, RoundedCornerShape(6.dp))
            .background(containerColor, RoundedCornerShape(6.dp))
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = tag.name,
            fontSize = 12.sp,
            color = if (tag.isWhite) ColorsPreset.white else ColorsPreset.black
        )
        Icon(
            modifier = Modifier
                .padding(start = 6.dp)
                .size(18.dp)
                .clickable { onDeleteClick(tag.id) },
            imageVector = Icons.Filled.Close,
            contentDescription = "Delete tag"
        )
    }
}