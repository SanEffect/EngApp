package com.san.englishbender.android.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun EBOutlinedButton(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit = 14.sp,
    textColor: Color = Color.DarkGray,
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
        Text(
            text = text,
            fontSize = fontSize,
            color = textColor
        )
    }
}