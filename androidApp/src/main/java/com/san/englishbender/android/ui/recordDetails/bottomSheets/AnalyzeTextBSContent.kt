package com.san.englishbender.android.ui.recordDetails.bottomSheets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.san.englishbender.ui.recordDetails.RecordDetailViewModel


@Composable
fun AnalyseTextBSContent(
    viewModel: RecordDetailViewModel,
    text: String
) {
    val result = viewModel.textResult.collectAsState().value

    LaunchedEffect(viewModel) {
        viewModel.checkGrammar(text)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            //.weight(1f, false)
            .padding(16.dp)
    ) {
        result.forEach {
            Text(it)
        }
    }
}