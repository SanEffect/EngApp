package com.san.englishbender.android.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import database.Label

@Composable
fun LabelItem(
    modifier: Modifier = Modifier,
    label: Label,
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
            text = label.name,
            fontSize = 12.sp
        )
        Icon(
            modifier = Modifier
                .padding(start = 6.dp)
                .size(18.dp)
                .clickable { onDeleteClick(label.id) },
            imageVector = Icons.Filled.Close,
            contentDescription = "Delete label"
        )
    }
}