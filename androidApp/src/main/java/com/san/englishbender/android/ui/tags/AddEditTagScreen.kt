package com.san.englishbender.android.ui.tags

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.san.englishbender.android.core.extensions.noRippleClickable
import com.san.englishbender.android.core.extensions.toColor
import com.san.englishbender.android.core.extensions.toHex
import com.san.englishbender.android.ui.common.DialogNavHeader
import com.san.englishbender.android.ui.common.EBOutlinedButton
import com.san.englishbender.android.ui.common.EBOutlinedIconButton
import com.san.englishbender.android.ui.common.FontColorChangeButton
import com.san.englishbender.android.ui.theme.ColorsPreset
import com.san.englishbender.android.ui.theme.RedDark
import com.san.englishbender.android.ui.theme.selectedLabelColor
import com.san.englishbender.core.extensions.isNull
import com.san.englishbender.domain.entities.TagEntity
import com.san.englishbender.randomUUID
import com.san.englishbender.ui.TagsViewModel
import io.github.aakira.napier.log
import kotlinx.coroutines.launch


@Composable
fun AddEditTagScreen(
    tagId: String? = null,
    tagsViewModel: TagsViewModel,
    onColorPicker: () -> Unit,
    onBack: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()
    val uiState by tagsViewModel.uiState.collectAsStateWithLifecycle()
    val tag = uiState.tags.firstOrNull { it.id == tagId }
    var name by remember { mutableStateOf(tag?.name ?: "") }
    var selectedColor by remember { mutableStateOf(tag?.color?.toColor ?: ColorsPreset.coral) }
    var isWhiteFontColor by remember { mutableStateOf(tag?.isWhite ?: false) }
    var confirmDeleting by remember { mutableStateOf(false) }

    val title = if (tagId.isNull) "Add new tag" else "Edit tag"

//    LaunchedEffect(tagsViewModel) {
//        log(tag = "TagsScreen") { "tagsViewModel.getColorPresets()" }
//        tagsViewModel.getColorPresets()
//    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        DialogNavHeader(
            title = title,
            onClick = onBack
        )

        // --- Name
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                value = name,
                singleLine = true,
                placeholder = { Text("Tag name", fontSize = 14.sp) },
                onValueChange = { name = it }
            )
            FontColorChangeButton(
                state = isWhiteFontColor,
                onClick = { isWhiteFontColor = !isWhiteFontColor }
            )
        }

        LazyHorizontalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(vertical = 16.dp)
                .background(Color.White),
            rows = GridCells.Fixed(2),
        ) {
            items(ColorsPreset.values.size) { index ->
                val colorPreset = ColorsPreset.values[index]
                val border = when (selectedColor == colorPreset) {
                    true -> Modifier.border(2.dp, selectedLabelColor, RoundedCornerShape(4.dp))
                    false -> Modifier.border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                }
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .padding(4.dp)
                        .background(
                            colorPreset,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .then(border)
                        .noRippleClickable {
                            selectedColor = colorPreset
                            focusManager.clearFocus()
                        }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // --- Buttons
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            EBOutlinedIconButton(
                imageVector = Icons.Outlined.Palette,
                onClick = { onColorPicker() }
            )
            tagId?.let {
                Spacer(Modifier.weight(1f))
                EBOutlinedButton(
                    modifier = Modifier.padding(end = 16.dp),
                    text = if (confirmDeleting) "Are you sure?" else "Delete",
                    textColor = if (confirmDeleting) Color.White else RedDark,
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (confirmDeleting) RedDark else Color.White
                    ),
                    contentPadding = PaddingValues(horizontal = 8.dp),
                    onClick = {
                        when (confirmDeleting) {
                            true -> {
                                coroutineScope.launch {
                                    tagsViewModel.deleteTag(it).join()
                                    onBack()
                                }
                            }

                            false -> confirmDeleting = true
                        }
                    }
                )
            }
            EBOutlinedButton(
                text = "Save",
                onClick = {
//                        val randomColor = Color(
//                            red = (0x00..0xFF).random(),
//                            green = (0x00..0xFF).random(),
//                            blue = (0x00..0xFF).random()
//                        )

                    log(tag = "isWhiteFontColor") { "saveTag isWhiteFontColor: $isWhiteFontColor" }

                    coroutineScope.launch {
                        tagsViewModel.saveTag(
                            TagEntity(
                                id = tagId ?: randomUUID(),
                                name = name,
                                color = selectedColor.toHex(),
                                isWhite = isWhiteFontColor
                            )
                        )
                        onBack()
                    }
                }
            )
        }

    }
}

//@Preview
@Composable
fun LazyHPreview() {

    val colors = listOf(
        Color.Red,
        Color.Yellow,
        Color.Green,
        Color.Blue,
        Color.White,
        Color.Black
    )

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth()
//            .height(180.dp)
            .padding(vertical = 8.dp)
            .background(Color.White),
        columns = GridCells.Fixed(5),
    ) {
        items(10) {
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .padding(4.dp)
                    .background(colors.random(), shape = RoundedCornerShape(4.dp))
                    .border(1.dp, Color.DarkGray, RoundedCornerShape(4.dp)),
            ) {

            }
        }
    }
}

//@Preview
@Composable
fun textView() {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = "",
        placeholder = { Text("Label name", fontSize = 14.sp, textAlign = TextAlign.Center) },
        onValueChange = { }
    )
}


@Preview
@Composable
fun RowPreview() {
    Row(
        modifier = Modifier
            .background(Color.White)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            value = "",
            singleLine = true,
            placeholder = { Text("Tag name", fontSize = 14.sp) },
            onValueChange = { }
        )
        FontColorChangeButton(
            state = true,
            onClick = { }
        )
    }
}

