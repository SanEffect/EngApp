package com.san.englishbender.android.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun EBOutlinedTextField(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String = "",
    singleLine: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Next
    ),
    onValueChange: (String) -> Unit = {}
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        singleLine = singleLine,
        placeholder = { Text(placeholder) },
        keyboardOptions = keyboardOptions,
        onValueChange = onValueChange
    )
}