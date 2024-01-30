package com.san.englishbender.android.ui.tags

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.san.englishbender.android.core.extensions.toColor
import com.san.englishbender.android.ui.common.EBOutlinedButton
import com.san.englishbender.android.ui.theme.ColorsPreset
import com.san.englishbender.android.ui.theme.selectedLabelColor
import com.san.englishbender.domain.entities.TagEntity
import com.san.englishbender.ui.TagsViewModel
import io.github.aakira.napier.log


@Composable
fun TagsScreen(
    tagsViewModel: TagsViewModel,
    recordTags: List<TagEntity>,
    addEditTag: (TagEntity?) -> Unit = {},
    onTagClick: (List<TagEntity>) -> Unit,
    dismiss: () -> Unit = {}
) {
    val uiState by tagsViewModel.uiState.collectAsStateWithLifecycle()
//    LaunchedEffect(tagsViewModel) {
//        log(tag = "TagsScreen") { "tagsViewModel.getTags()" }
//        tagsViewModel.getTags()
//    }

    val selectedTags = remember { mutableStateListOf<TagEntity>() }
    recordTags.forEach {
        if (!selectedTags.contains(it)) selectedTags.add(it)
    }

    Column(modifier = Modifier.padding(16.dp)) {

        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp),
            text = "Tags",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )

        Column(modifier = Modifier
            .weight(1f)
            .padding(bottom = 16.dp)) {
            LazyColumn {
                items(uiState.tags.size) { index ->
                    val tag = uiState.tags[index]
                    val hasTag = selectedTags.any { it.id == tag.id }

                    TagRow(
                        tag = tag,
                        isSelected = hasTag,
                        onClick = {
                            if (hasTag) selectedTags.removeIf { it.id == tag.id }
                            else selectedTags.add(tag)

                            onTagClick(selectedTags.toList())
                        },
                        onEditClick = { addEditTag(tag) }
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            EBOutlinedButton(
                text = "Create",
                onClick = { addEditTag(null) }
            )

            EBOutlinedButton(
                text = "Close",
                onClick = dismiss
            )
        }
    }
}

@Composable
fun TagRow(
    tag: TagEntity,
    isSelected: Boolean = false,
    onClick: (String) -> Unit,
    onEditClick: () -> Unit,
) {
    val border = when (isSelected) {
        true -> Modifier.border(2.dp, selectedLabelColor, RoundedCornerShape(4.dp))
        false -> Modifier.border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
    }
    Row(
        Modifier
            .width(300.dp)
            .padding(2.dp)
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(7f)
                .padding(2.dp)
                .background(tag.color.toColor, shape = RoundedCornerShape(4.dp))
                .then(border)
                .clickable { onClick(tag.id) }
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = tag.name,
                fontSize = 12.sp,
                color = if (tag.isWhite) ColorsPreset.white else ColorsPreset.black
            )
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(2.dp)
                .background(Color.White)
                .border(1.dp, Color.DarkGray, RoundedCornerShape(4.dp))
                .clickable { onEditClick() },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                Icons.Outlined.Edit,
                contentDescription = null,
                modifier = Modifier
                    .padding(6.dp)
                    .size(18.dp)
            )
        }
    }
}

@Preview
@Composable
fun LabelRowPreview(

) {
    Row(
        Modifier
            .width(300.dp)
            .padding(2.dp)
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(7f)
                .padding(2.dp)
                .background(Color.Green, shape = RoundedCornerShape(4.dp))
                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                .clickable { }
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Label",
                fontSize = 12.sp
            )
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(2.dp)
                .background(Color.White)
                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                Icons.Outlined.Edit,
                contentDescription = null,
                modifier = Modifier
                    .padding(6.dp)
                    .size(18.dp)
                    .clickable { }
            )
        }

    }
}