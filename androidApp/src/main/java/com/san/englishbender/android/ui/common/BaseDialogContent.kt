package com.san.englishbender.android.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.san.englishbender.android.core.extensions.noRippleClickable

@Composable
fun BaseDialogContent(
    modifier: Modifier = Modifier,
    width: Dp = 300.dp,
    height: Dp = 450.dp,
    shape: Shape = RoundedCornerShape(12.dp),
    containerColor: Color = Color.White,
    dismiss: () -> Unit = {},
    content: @Composable () -> Unit,
) {
//    val heightDp = height?.let { Modifier.height(height) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .noRippleClickable { dismiss() }
    ) {
        Box(
            modifier = modifier
                .align(Alignment.Center)
//                .then(heightDp)
                .width(width)
                .height(height)
                .clip(shape)
                .background(containerColor)
                .noRippleClickable {}
        ) {
            content()
        }
    }
}