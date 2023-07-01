package com.san.englishbender.android.ui.recordDetails.bottomSheets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun BackgroundColorPickerBSContent(
    modifier: Modifier = Modifier,
    label: String = "Background Color",
    listState: LazyListState = rememberLazyListState(),
    onClick: (color: Color) -> Unit
) {
    val colors = listOf(
        Color(0xFFFFFFFF),
        Color(0xFFFFFFCC),
        Color(0xFFFFCC99),
        Color(0xFFFFCCCC),
        Color(0xFFFFCCFF),
        Color(0xFFCCCCFF),
        Color(0xFF99CCFF),
        Color(0xFFCCFFFF),
        Color(0xFF99FFCC),
        Color(0xFFCCFF99),
    )

    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(8.dp),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            text = label
        )

        LazyRow(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(colors.size) { index ->
                val color = colors[index]
                Card(
                    modifier = Modifier
                        .size(60.dp)
                        .clickable(onClick = { onClick(color) }),
                    colors = CardDefaults.cardColors(containerColor = color),
                    border = BorderStroke(1.dp, Color.LightGray)
//                    border = if (index == 0) BorderStroke(1.dp, Color.LightGray) else null
                ) {}
            }
        }
    }
}

//@Composable
//fun BackgroundColorPickerBSContent(onClickColor: (color: Color) -> Unit) {
//
//    val colors = listOf(
//        Color(0xFFFFFFFF),
//        Color(0xFFFFFFCC),
//        Color(0xFFFFCC99),
//        Color(0xFFFFCCCC),
//        Color(0xFFFFCCFF),
//        Color(0xFFCCCCFF),
//        Color(0xFF99CCFF),
//        Color(0xFFCCFFFF),
//        Color(0xFF99FFCC),
//        Color(0xFFCCFF99),
//    )
//
//    val spacedBy = 16.dp
//    val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp
//    val screenWidthThird = screenWidthDp / 6
//    val itemSize: Dp = screenWidthThird - spacedBy - (spacedBy / 3)
//
//    Box(
//        modifier = Modifier
//            .height(300.dp)
//            .padding(16.dp)
//    )
//    {
//        LazyHorizontalGrid(
//            rows = GridCells.Fixed(2),
//            contentPadding = PaddingValues(spacedBy),
//            horizontalArrangement = Arrangement.spacedBy(spacedBy),
//            verticalArrangement = Arrangement.spacedBy(spacedBy)
//        ) {
//            items(colors.size) { index ->
//                val color = colors[index]
//                Card(
//                    modifier = Modifier
//                        .size(itemSize)
//                        .clickable(onClick = { onClickColor(color) }),
//                    colors = CardDefaults.cardColors(
//                        containerColor = color
//                    ),
//                    border = if (index == 0) BorderStroke(1.dp, Color.LightGray) else null
//                ) {}
//            }
//        }
//    }
//}