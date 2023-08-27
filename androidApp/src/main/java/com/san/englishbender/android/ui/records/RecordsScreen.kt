package com.san.englishbender.android.ui.records

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.san.englishbender.android.core.extensions.truncateText
import com.san.englishbender.core.AppConstants.RECORD_MAX_LENGTH_DESCRIPTION
import com.san.englishbender.core.AppConstants.RECORD_MAX_LENGTH_TITLE
import com.san.englishbender.core.utils.DateConverters.convertLongToDate
import com.san.englishbender.domain.entities.RecordEntity
import com.san.englishbender.ui.records.RecordsViewModel
import io.github.aakira.napier.log
import org.koin.androidx.compose.getViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordsScreen(
    onRecordClick: (recordId: String?) -> Unit,
    openDrawer: () -> Unit

) {
    log(tag = "navigationFuck") { "RecordsScreen" }
//    val appContext: Context = remember { GlobalContext.get().get() }
//    val lifecycleOwner = LocalLifecycleOwner.current
//    val context = LocalContext.current

    val viewModel: RecordsViewModel = getViewModel()
    val uiState by viewModel.recordsUiState.collectAsStateWithLifecycle()

//    when(uiState) {
//        is RecordsUiState.Loading -> LoadingView()
//        is RecordsUiState.Success -> {
//            RecordsContent(
//                onRecordClick,
//                viewModel,
//                uiState.cast<RecordsUiState.Success>().records
//            )
//        }
//        is RecordsUiState.Failure -> {
//            ErrorView(e = uiState.cast<RecordsUiState.Failure>().exception)
//        }
//    }

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = {},
                navigationIcon = {
                    Icon(
                        rememberVectorPainter(Icons.Filled.Menu),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable { openDrawer() }
                    )
                },
                actions = {
                    Icon(
                        rememberVectorPainter(Icons.Filled.FilterList),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable { }
                    )
                    Icon(
                        rememberVectorPainter(Icons.Filled.Settings),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable { }
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                contentColor = Color.White,
                backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
                shape = RoundedCornerShape(10.dp),
                onClick = {
                    onRecordClick(null)
                }
            ) {
                Icon(
                    Icons.Filled.Edit,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    ) { innerPadding ->

//        val listState = rememberLazyListState()
//
//        val isFirst by remember { derivedStateOf {
//            listState.layoutInfo.visibleItemsInfo.firstOrNull()?.index == 0
//        } }
//
//        val isEnabled by remember {
//            derivedStateOf { listState.firstVisibleItemIndex > 0 }
//        }

//        val isScrolledToTheTopDerived = listState.isScrolledToTheTopDerived()
//        log(tag = "rememberLazyListState") { "isTheTop: ${listState.isScrolledToTheTop()}" }
//        log(tag = "rememberLazyListState") { "isTheTopDerived: ${isScrolledToTheTopDerived.value}" }

        LazyColumn(
//            state = listState,
            modifier = Modifier.padding(innerPadding)
        ) {
//            items(40) {
//                Text("Text for check")
//            }
            items(uiState.records.size) { index ->
                val record = uiState.records[index]
                RecordItem(record, viewModel, onRecordClick)
            }
        }
    }
}

@Composable
fun LazyListState.isScrolledToTheTopDerived() = remember { derivedStateOf { layoutInfo.visibleItemsInfo.firstOrNull()?.index == 0 } }


fun LazyListState.isScrolledToTheTop() = layoutInfo.visibleItemsInfo.firstOrNull()?.index == 0

@Composable
fun RecordItem(
    record: RecordEntity,
    viewModel: RecordsViewModel,
    onRecordClick: (recordId: String?) -> Unit,
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    record.title = truncateText(record.title, RECORD_MAX_LENGTH_TITLE)
    record.description = truncateText(record.description, RECORD_MAX_LENGTH_DESCRIPTION)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        onRecordClick(record.id)
                    },
                    onLongPress = { showDeleteDialog = true }
                )
            },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column(
            modifier = Modifier.padding(
                top = 18.dp,
                bottom = 18.dp,
                start = 12.dp,
                end = 12.dp
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    fontSize = 14.sp,
                    text = convertLongToDate(record.creationDate)
                )
                Icon(
                    Icons.Outlined.StarOutline,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            Text(
                modifier = Modifier.padding(bottom = 4.dp),
                fontSize = 16.sp,
                text = record.title
            )
            Text(text = record.description, fontSize = 14.sp)
        }
    }

    if (showDeleteDialog) {
        DeleteRecordDialog(
            confirmButton = {
                viewModel.removeRecord(record)
                showDeleteDialog = false
            },
            dismissButton = { showDeleteDialog = false }
        )
    }
}

@Composable
fun DeleteRecordDialog(
    confirmButton: () -> Unit,
    dismissButton: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { dismissButton() },
        title = { androidx.compose.material.Text("Deleting") },
        text = { androidx.compose.material.Text("Are you sure?") },
        confirmButton = {
            TextButton(
                onClick = { confirmButton() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent,
                    contentColor = Color.White
                )
            ) {
                Text("Yes")
            }
        },
        dismissButton = {
            TextButton(
                onClick = { dismissButton() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent,
                    contentColor = Color.White
                )
            ) {
                Text("Cancel")
            }
        },
        backgroundColor = Color.White
    )
}