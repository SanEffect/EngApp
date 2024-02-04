package com.san.englishbender.android.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FontDownload
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.san.englishbender.android.core.extensions.noRippleClickable


@Composable
fun EBOutlinedButton(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit = 14.sp,
    textColor: Color = Color.DarkGray,
    shape: RoundedCornerShape = RoundedCornerShape(4.dp),
    borderStroke: BorderStroke = BorderStroke(1.dp, Color.Gray),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(contentColor = Color.DarkGray),
    onClick: () -> Unit,
) {
    OutlinedButton(
        modifier = modifier,
        shape = shape,
        border = borderStroke,
        colors = colors,
        contentPadding = contentPadding,
        onClick = onClick
    ) {
        Text(
            text = text,
            fontSize = fontSize,
            color = textColor
        )
    }
}

@Composable
fun EBOutlinedIconButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    containerColor: Color = Color.White,
    shape: RoundedCornerShape = RoundedCornerShape(4.dp),
    borderStroke: BorderStroke = BorderStroke(1.dp, Color.Gray),
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(contentColor = Color.DarkGray),
    onClick: () -> Unit,
) {
    OutlinedButton(
        modifier = modifier,
        shape = shape,
        border = borderStroke,
        colors = colors,
        contentPadding = PaddingValues(0.dp),
        onClick = onClick
    ) {
        Icon(imageVector, contentDescription = null)
    }
}

@Composable
fun FontColorChangeButton(
    modifier: Modifier = Modifier,
    state: Boolean = false,
    shape: RoundedCornerShape = RoundedCornerShape(4.dp),
    borderStroke: BorderStroke = BorderStroke(1.dp, Color.DarkGray),
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(),
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .size(54.dp)
            .border(1.dp, Color.Gray, shape)
            .background(if (state) Color.Black else Color.White, shape)
            .noRippleClickable { onClick() },
        contentAlignment = Alignment.Center
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text ="A",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = if (state) Color.White else Color.Black
        )
    }
//    Icon(
//        modifier = modifier
//            .size(48.dp)
//            .background(if (state) Color.Black else Color.White, RoundedCornerShape(4.dp))
//            .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
//            .clickable { onClick() },
//        imageVector = Icons.Filled.FontDownload,
//        contentDescription = null,
//        tint = if (state) Color.White else Color.Black
//    )
//    OutlinedButton(
//        modifier = modifier.width(56.dp).height(56.dp),
//        shape = shape,
//        border = borderStroke,
//        colors = colors,
//        contentPadding = PaddingValues(4.dp),
//        onClick = onClick
//    ) {
//        Icon(
//            modifier = Modifier.size(56.dp).padding(0.dp).background(if (state) Color.Black else Color.White),
//            imageVector = Icons.Filled.FontDownload,
//            contentDescription = null,
//            tint = if (state) Color.White else Color.Black
//        )
//    }
}