package com.san.englishbender.android.ui.records

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.san.englishbender.android.ui.common.FloatingAddRecordButton
import com.san.englishbender.android.ui.common.widgets.EmptyView
import com.san.englishbender.android.ui.common.widgets.ErrorView
import com.san.englishbender.android.ui.common.widgets.LoadingView
import com.san.englishbender.android.ui.destinations.RecordDetailScreenDestination
import com.san.englishbender.core.AppConstants.RECORD_MAX_LENGTH_DESCRIPTION
import com.san.englishbender.core.extensions.cast
import com.san.englishbender.core.extensions.truncateText
import com.san.englishbender.core.extensions.truncateTitle
import com.san.englishbender.core.utils.DateConverters.convertLongToDate
import com.san.englishbender.domain.entities.Record
import com.san.englishbender.ui.common.base.BaseViewState
import com.san.englishbender.ui.records.GetRecordsState
import com.san.englishbender.ui.records.RecordsViewModel
import kotlinx.coroutines.flow.Flow
import org.koin.androidx.compose.getViewModel
import timber.log.Timber


@Destination(start = true)
@Composable
fun RecordsScreen(
    navigator: DestinationsNavigator
) {
//    val appContext: Context = remember { GlobalContext.get().get() }
//    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    val viewModel: RecordsViewModel = getViewModel()
    val uiState by viewModel.uiState.collectAsState()

    Timber.tag("navigation").d("RecordsScreen")

    when (uiState) {
        is BaseViewState.Data -> {
            Timber.tag("navigation").d("BaseViewState.Data")
            val data = uiState.cast<BaseViewState.Data<GetRecordsState>>().value
            val pagingItems = rememberFlowWithLifecycle(data.pagedData).collectAsLazyPagingItems()

            RecordsContent(
                navigator,
                viewModel,
                pagingItems
            )
        }

        is BaseViewState.Empty -> {
            Timber.tag("navigation").d("BaseViewState.Empty")
            EmptyView()
        }
        is BaseViewState.Error -> {
            Timber.tag("navigation").d("BaseViewState.Error")
            ErrorView(
                e = uiState.cast<BaseViewState.Error>().throwable,
                action = {
//                viewModel.loadRecordsPaging()
                }
            )
        }

        is BaseViewState.Loading -> {
            Timber.tag("navigation").d("BaseViewState.Loading")
            LoadingView()
        }
    }

//    LaunchedEffect(key1 = viewModel, block = {
//        viewModel.saveRecord()
//    })

    LaunchedEffect(viewModel) {
        Timber.tag("navigation").d("loadRecordsPaging")
        viewModel.loadRecordsPaging()
    }
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

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RecordsContent(
    navigator: DestinationsNavigator,
    viewModel: RecordsViewModel,
    pagingItems: LazyPagingItems<Record>,
) {

//    val navigator = LocalNavigator.currentOrThrow
//    val pagingItems = rememberFlowWithLifecycle(data.pagedData).collectAsLazyPagingItems()

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = {},
                navigationIcon = {
                    Icon(
                        rememberVectorPainter(Icons.Filled.Menu),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(8.dp)
                        //.clickable { openDrawer() }
                    )
                },
                actions = {
                    Icon(
                        rememberVectorPainter(Icons.Filled.Settings),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(8.dp)
                        //.clickable { openDrawer() }
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                contentColor = Color.White,
                backgroundColor = FloatingAddRecordButton,
                shape = RoundedCornerShape(10.dp),
                onClick = { navigator.navigate(RecordDetailScreenDestination(null)) }
            ) {
                Icon(
                    Icons.Filled.Edit,
                    contentDescription = ""
                )
            }
        }
    ) { paddingValues ->

//        Timber.tag("navigation").d("pagingItems.itemCount: ${pagingItems.itemCount}")

        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(pagingItems.itemCount) { index ->
                pagingItems[index]?.let {
                    RecordItem(it, viewModel, navigator)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecordItem(
    record: Record,
    viewModel: RecordsViewModel,
    navigator: DestinationsNavigator,
) {
    Timber.tag("navigation").d("record: ${record.title}")

    var showMenu by remember { mutableStateOf(false) }

    record.title = truncateTitle(record.title)
//    record.description = truncateDescription(record.description)
    record.description = truncateText(record.description, RECORD_MAX_LENGTH_DESCRIPTION)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        navigator.navigate(RecordDetailScreenDestination(record.id))
                    },
                    onLongPress = {
//                        Timber.tag("popup").d("showPopup onLongPress")
                        showMenu = true
                    }
                )
            },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = (record.title))
                Text(text = convertLongToDate(record.creationDate))
            }
            Text(text = record.description)
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