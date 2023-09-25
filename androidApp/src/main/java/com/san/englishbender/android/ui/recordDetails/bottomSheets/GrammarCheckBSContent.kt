package com.san.englishbender.android.ui.recordDetails.bottomSheets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.san.englishbender.android.ui.common.widgets.LoadingView
import com.san.englishbender.ui.recordDetails.RecordDetailsViewModel


@Composable
fun GrammarCheckBSContent(
    viewModel: RecordDetailsViewModel,
    text: String
) {
    val uiState = viewModel.grammarUiState.collectAsStateWithLifecycle().value

    LaunchedEffect(viewModel) {
        viewModel.checkGrammar(text)
    }

    when {
        uiState.isLoading -> LoadingView(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(bottom = 40.dp)
        )
        else -> Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 38.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            uiState.result.forEach { Text(it) }
        }
    }
}