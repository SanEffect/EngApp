@file:JvmName("AddLabelScreenKt")

package com.san.englishbender.android.ui.tags

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.san.englishbender.android.ui.common.BaseDialogContent
import com.san.englishbender.android.ui.common.EBOutlinedButton
import com.san.englishbender.core.extensions.isNull
import com.san.englishbender.ui.TagsViewModel


@Composable
fun AddTagScreen(
    tagId: String? = null,
    tagsViewModel: TagsViewModel,
    onColorPicker: () -> Unit,
    onBack: () -> Unit,
    dismiss: () -> Unit = {}
) {
    val uiState by tagsViewModel.uiState.collectAsStateWithLifecycle()
    var name by remember { mutableStateOf("") }
    var hexCode by remember { mutableStateOf("") }
    var color: Color by remember { mutableStateOf(Color.Black) }

    val tag = uiState.tags.firstOrNull { it.id == tagId }
    val title = if (tagId.isNull) "Add label" else "Edit label"

    LaunchedEffect(Unit) {
        tagsViewModel.getTagColors()
    }

    BaseDialogContent(
        height = 350.dp,
        dismiss = dismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp),
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            // --- Name
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = name,
                placeholder = { Text("Label name") },
                onValueChange = { name = it }
            )

            // --- Color presets
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
                    .padding(vertical = 16.dp)
                    .background(Color.White),
                columns = GridCells.Fixed(6),
            ) {
                items(12) { index ->
                    if (index == 11) {
                        Box(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .padding(4.dp)
                                .background(Color.White, shape = RoundedCornerShape(4.dp))
                                .border(1.dp, Color.DarkGray, RoundedCornerShape(4.dp))
                                .clickable { onColorPicker() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Outlined.Palette, contentDescription = "Color Picker")
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .padding(4.dp)
                                .background(colors.random(), shape = RoundedCornerShape(4.dp))
                                .border(1.dp, Color.DarkGray, RoundedCornerShape(4.dp)),
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // --- Button
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                EBOutlinedButton(
                    text = "Back",
                    onClick = onBack
                )
                EBOutlinedButton(
                    text = "Add",
                    onClick = {
                        val randomColor = Color(
                            red = (0x00..0xFF).random(),
                            green = (0x00..0xFF).random(),
                            blue = (0x00..0xFF).random()
                        )

//                        labelsViewModel.saveLabel(
//                            Label(
//                                id = "",
//                                name = name,
//                                color = randomColor.toHex()
//                            )
//                        )
                    }
                )
            }

        }
    }
}

@Preview
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