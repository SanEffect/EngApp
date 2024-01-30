package com.san.englishbender.android.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DialogHeader(
    title: String,
    onClick: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
        Row(Modifier.weight(1f)) {
            Icon(
                modifier = Modifier.clickable { onClick() },
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = null
            )
        }
        Row(Modifier.weight(2f)) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
        Row(Modifier.weight(1f)) {}
    }
}