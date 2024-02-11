package com.san.englishbender.android.ui.common

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.san.englishbender.android.core.extensions.noRippleClickable
import com.san.englishbender.android.ui.theme.backgroundColors
import com.san.englishbender.android.ui.theme.selectedLabelColor
import com.san.englishbender.core.extensions.ifNotEmpty
import io.github.aakira.napier.log

@Composable
fun BackgroundColorPicker(
    modifier: Modifier = Modifier,
    label: String = "Background Color",
    listState: LazyListState = rememberLazyListState(),
    onClick: (color: Color) -> Unit
) {
    var selectedColor by remember { mutableStateOf(backgroundColors.first()) }

    Column(modifier = modifier) {
        label.ifNotEmpty {
            Text(
                modifier = Modifier.padding(8.dp),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                text = label
            )
        }

        LazyRow(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(backgroundColors.size) { index ->
                val color = backgroundColors[index]
                val border = when (selectedColor == color) {
                    true -> Modifier.border(2.dp, selectedLabelColor, RoundedCornerShape(4.dp))
                    false -> Modifier.border(1.dp, Color.LightGray, RoundedCornerShape(4.dp))
                }
                Card(
                    modifier = Modifier
                        .size(60.dp)
                        .then(border)
                        .noRippleClickable {
                            selectedColor = color
                            log(tag = "containerColor") { "onClick: $color" }
                            onClick(color)
                        },
                    colors = CardDefaults.cardColors(containerColor = color)
                ) {}
            }
        }
    }
}