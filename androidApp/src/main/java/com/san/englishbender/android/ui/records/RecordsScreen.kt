package com.san.englishbender.android.ui.records

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.san.englishbender.android.core.extensions.truncateText
import com.san.englishbender.android.ui.common.widgets.EmptyView
import com.san.englishbender.android.ui.common.widgets.ErrorView
import com.san.englishbender.android.ui.common.widgets.LoadingView
import com.san.englishbender.core.AppConstants.RECORD_MAX_LENGTH_DESCRIPTION
import com.san.englishbender.core.AppConstants.RECORD_MAX_LENGTH_TITLE
import com.san.englishbender.core.extensions.isNotNull
import com.san.englishbender.core.utils.DateConverters.convertLongToDate
import com.san.englishbender.domain.entities.RecordEntity
import com.san.englishbender.ui.records.RecordsUiState
import com.san.englishbender.ui.records.RecordsViewModel
import org.koin.androidx.compose.getViewModel


@Composable
fun RecordsScreen(
    onRecordClick: (String?) -> Unit,
    openDrawer: () -> Unit
) {
//    val appContext: Context = remember { GlobalContext.get().get() }
//    val lifecycleOwner = LocalLifecycleOwner.current

    val viewModel: RecordsViewModel = getViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    RecordsContent(
        viewModel,
        uiState,
        onRecordClick = onRecordClick,
        openDrawer = openDrawer
    )

    when {
        uiState.isLoading -> LoadingView()
        uiState.userMessage.isNotNull -> ErrorView(userMessage = uiState.userMessage)
        uiState.records.isEmpty() -> EmptyView()
        else -> RecordsContent(
            viewModel,
            uiState,
            onRecordClick = onRecordClick,
            openDrawer = openDrawer
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordsContent(
    viewModel: RecordsViewModel,
    uiState: RecordsUiState,
    onRecordClick: (String?) -> Unit,
    openDrawer: () -> Unit
) {
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
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(items = uiState.records, key = { it.id }) { record ->
                RecordItem(record, viewModel, onRecordClick)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecordItem(
    record: RecordEntity,
    viewModel: RecordsViewModel,
    onRecordClick: (String?) -> Unit,
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    record.title = truncateText(record.title, RECORD_MAX_LENGTH_TITLE)
    record.description = truncateText(record.description, RECORD_MAX_LENGTH_DESCRIPTION)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .combinedClickable(
                onClick = { onRecordClick(record.id) },
                onLongClick = { showDeleteDialog = true }
            ),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column(
            modifier = Modifier.padding(
                vertical = 18.dp,
                horizontal = 12.dp
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
        title = { Text("Deleting") },
        text = { Text("Are you sure?") },
        confirmButton = {
            TextButton(
                onClick = { confirmButton() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent,
                    contentColor = Color.Red
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