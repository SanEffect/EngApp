package com.san.englishbender.android.ui.records

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.san.englishbender.android.core.truncateText
import com.san.englishbender.android.ui.common.widgets.ErrorView
import com.san.englishbender.android.ui.common.widgets.LoadingView
import com.san.englishbender.core.AppConstants.RECORD_MAX_LENGTH_DESCRIPTION
import com.san.englishbender.core.AppConstants.RECORD_MAX_LENGTH_TITLE
import com.san.englishbender.core.extensions.cast
import com.san.englishbender.core.utils.DateConverters.convertLongToDate
import com.san.englishbender.domain.entities.Record
import com.san.englishbender.ui.records.RecordsUiState
import com.san.englishbender.ui.records.RecordsViewModel
import kotlinx.coroutines.flow.Flow
import org.koin.androidx.compose.getViewModel
import timber.log.Timber


@Composable
fun RecordsScreen(
    onRecordClick: (recordId: String?) -> Unit
) {
//    val appContext: Context = remember { GlobalContext.get().get() }
//    val lifecycleOwner = LocalLifecycleOwner.current
//    val context = LocalContext.current

    val viewModel: RecordsViewModel = getViewModel()

//    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uiState by viewModel.recordsUiState.collectAsStateWithLifecycle()

//    (uiState as? RecordsUiState.Success)?.let {
//        Text(text = it.records.firstOrNull()?.title.toString(), color = Color.White)
//    }

//    val data = uiState.cast<RecordsUiState.Success>().records
//    val pagingItems = rememberFlowWithLifecycle(recordsUiState.records).collectAsLazyPagingItems()

//    Timber.tag("onCleared").d("pagingItems: $pagingItems")

    //Timber.tag("onCleared").d("RecordsScreen")

    when(uiState) {
        is RecordsUiState.Loading -> {
            //Timber.tag("onCleared").d("RecordsScreen Loading")
            LoadingView()
        }
        is RecordsUiState.Success -> {
            //Timber.tag("onCleared").d("RecordsScreen Success")
            val records = uiState.cast<RecordsUiState.Success>().records
//            val pagingItems = rememberFlowWithLifecycle(data).collectAsLazyPagingItems()

//            Timber.tag("onCleared").d("itemCount: ${pagingItems.itemCount}")

            RecordsContent(
                onRecordClick,
                viewModel,
                records
            )

//            LazyColumn(
//                verticalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                items(pagingItems.itemCount) { index ->
//                    val record = pagingItems[index]
//
//                    Text(record?.title ?: "")
//                }
//
//                item {
//                    Button(onClick = onRecordClick) {
//                        Text("Go to Record")
//                    }
//                }
//            }
        }
        is RecordsUiState.Failure -> {
            Timber.tag("onCleared").d("RecordsScreen Failure")
            ErrorView(e = uiState.cast<RecordsUiState.Failure>().exception)
        }
    }

//    LaunchedEffect(key1 = viewModel, block = {
//        viewModel.saveRecord()
//    })

//    LaunchedEffect(Unit) {
//
//        viewModel.loadRecordsPaging()
//    }
//    Timber.tag("onCleared").d("--------------------------------")
}

@Composable
fun <T> rememberFlowWithLifecycle(
    flow: Flow<T>,
    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
): Flow<T> = remember(flow, lifecycle) {
    flow.flowWithLifecycle(
        lifecycle = lifecycle,
        minActiveState = minActiveState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordsContent(
    onRecordClick: (recordId: String?) -> Unit,
    viewModel: RecordsViewModel,
    records: List<Record>,
) {
//    val viewState by viewModel.viewState.collectAsState()

//    val navigator = LocalNavigator.currentOrThrow
//    val pagingItems = rememberFlowWithLifecycle(data.pagedData).collectAsLazyPagingItems()

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
                            .clickable { }
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
//                onClick = { navigator.navigate(RecordDetailScreenDestination(null)) }
            ) {
                Icon(
                    Icons.Filled.Edit,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    ) { paddingValues ->

//        Timber.tag("navigation").d("pagingItems.itemCount: ${pagingItems.itemCount}")

        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(records.size) { index ->
                val record = records[index]
                RecordItem(record, viewModel, onRecordClick)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecordItem(
    record: Record,
    viewModel: RecordsViewModel,
    onRecordClick: (recordId: String?) -> Unit,
) {
    Timber.tag("navigationLib").d("record: ${record.title}")

    var showMenu by remember { mutableStateOf(false) }

//    record.description = truncateDescription(record.description)
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
                    onLongPress = { showMenu = true }
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

        if (showMenu) {
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            ) {
                DropdownMenuItem(onClick = {}) {
                    Text("Refresh")
                }
                DropdownMenuItem(onClick = {}) {
                    Text("Settings")
                }
                Divider()
                DropdownMenuItem(onClick = {
//                    Timber.tag("Delete").d("Delete....")
//                    viewModel.onTriggerEvent(RecordsEvent.RemoveRecord(record.id))
                }) {
                    Text("Delete")
                }
            }
        }
    }
}