package com.san.englishbender.android.ui.recordDetails

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.NewLabel
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.san.englishbender.android.core.toColor
import com.san.englishbender.android.core.toHex
import com.san.englishbender.android.ui.common.LabelsDialog
import com.san.englishbender.android.ui.common.widgets.LoadingView
import com.san.englishbender.android.ui.recordDetails.bottomSheets.BackgroundColorPickerBSContent
import com.san.englishbender.android.ui.recordDetails.bottomSheets.TranslatedTextBSContent
import com.san.englishbender.android.ui.theme.BottomSheetContainerColor
import com.san.englishbender.android.ui.theme.RedDark
import com.san.englishbender.core.extensions.cast
import com.san.englishbender.domain.entities.Label
import com.san.englishbender.domain.entities.Record
import com.san.englishbender.ui.recordDetail.NavTarget
import com.san.englishbender.ui.recordDetail.RecordDetailViewModel
import com.san.englishbender.ui.recordDetail.RecordsDetailUiState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import timber.log.Timber
import java.util.Calendar
import java.util.Date


@Composable
//@ExperimentalMaterialApi
fun RecordDetailScreen(
    onBackClick: () -> Unit,
    recordId: String?
) {
    Timber.tag("onCleared").d("RecordDetailScreen (recordId: $recordId)")

    val coroutineScope = rememberCoroutineScope()
    val viewModel: RecordDetailViewModel = getViewModel()
//    val navigation by viewModel.navigation.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

//    LaunchedEffect(navigation) {
//        navigation.getContentIfNotHandled()?.let { navTarget ->
//            coroutineScope.launch {
//                Timber.tag("onCleared").d("navigation")
//                when (navTarget) {
//                    is NavTarget.RecordsScreen -> onBackClick()
//                    else -> {}
//                }
//            }
//        }
//    }

    when(uiState) {
        is RecordsDetailUiState.Loading -> {
            Timber.tag("onCleared").d("RecordDetailScreen Loading")
            LoadingView()
        }
        is RecordsDetailUiState.Success -> {
            Timber.tag("onCleared").d("RecordDetailContent Success")
            RecordDetailContent(
                onBackClick,
                viewModel,
                uiState.cast<RecordsDetailUiState.Success>().record,
            )
        }
        is RecordsDetailUiState.Empty -> {
            Timber.tag("onCleared").d("RecordDetailContent Empty")
            RecordDetailContent(
                onBackClick,
                viewModel,
                null,
            )
        }
        is RecordsDetailUiState.Failure -> {
            Timber.tag("onCleared").d("RecordDetailScreen Failure")
        }
    }

    LaunchedEffect(Unit) {
        when (recordId) {
            null -> {
                Timber.tag("onCleared").d("showEmptyScreen")
                viewModel.showEmptyScreen()
            }
            else -> viewModel.loadRecord(recordId)
        }
    }

//        DisposableEffect(LocalLifecycleOwner.current) {
//
//            Timber.tag("recordDesc").d("clear")
//            onDispose { viewModel.clear() }
//        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordDetailContent(
    onBackClick: () -> Unit,
    viewModel: RecordDetailViewModel,
    record: Record?,
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

//    var originalText by rememberSaveable(viewState) { mutableStateOf(viewState.originalText) }
//    val translatedText by rememberSaveable(viewState) { mutableStateOf(viewState.translatedText) }
//    val showTranslatedText by rememberSaveable(viewState) { mutableStateOf(viewState.showTranslatedText) }
//    val russianWordList by viewModel.russianWordList.collectAsState(listOf())

    val recordData by rememberSaveable(record) { mutableStateOf(record ?: Record()) }

    val randomGreeting = viewModel.randomGreeting
    var title by rememberSaveable { mutableStateOf(recordData.title) }
    var description by rememberSaveable { mutableStateOf(recordData.description) }

    var backgroundColor by remember {
        mutableStateOf(
            if (recordData.backgroundColor.isEmpty()) Color.White else recordData.backgroundColor.toColor
        )
    }
    var bottomNavItem by remember { mutableStateOf<BottomNavItem>(BottomNavItem.Translate) }

    var labelsDialog by remember { mutableStateOf(false) }

//    val snackbarMessage by viewModel.snackbar.collectAsState()

//    val formattedString = getAnnotatedString(description, russianWordList)
//    var textFieldValueState by remember {
//        mutableStateOf(TextFieldValue(annotatedString = formattedString))
//    }
//    val textFieldValue = textFieldValueState.copy(annotatedString = formattedString)
//
//    LaunchedEffect(snackbarMessage) {
//        snackbarMessage.getContentIfNotHandled()?.let {
//            coroutineScope.launch {
//                scaffoldState.snackbarHostState.showSnackbar(it)
//            }
//        }
//    }

//    DisposableEffect(LocalLifecycleOwner.current) {
//        //viewModel.onStart()
//        onDispose { viewModel.saveDraft(record) }
//    }

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        containerColor = backgroundColor,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor
                ),
                title = {
                    Text(
//                        stringResource(titleResId),
                        if (recordData.title.isEmpty()) "New Record" else "Record Details",
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth(),
//                        style = JetRortyTypography.h2
                    )
                },
                actions = {
                    Icon(
                        rememberVectorPainter(Icons.Outlined.Save),
                        contentDescription = null,
//                        tint = JetRortyColors.navigationBackIconColor,
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                coroutineScope.launch {
                                    viewModel.saveRecord(recordData)
                                }
                            }
                    )
                },
                navigationIcon = {
                    Icon(
                        rememberVectorPainter(Icons.Filled.ArrowBack),
                        contentDescription = null,
//                        tint = JetRortyColors.navigationBackIconColor,
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable { onBackClick() }
                    )
                },
                modifier = Modifier.fillMaxWidth(),
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = backgroundColor,
                navItemClicked = { navItem ->
                    bottomNavItem = navItem
                    openBottomSheet = true
                })
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
        ) {
            if (recordData.isDraft) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 12.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(text = "Draft", color = RedDark)
                }
            }

            Row(modifier = Modifier.padding(
                start = 12.dp,
                end = 12.dp,
                top = 12.dp,
                bottom = 24.dp
            )) {
                Icon(
                    rememberVectorPainter(Icons.Outlined.NewLabel),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            labelsDialog = true
//                            viewModel.insertTags()
                        }
                )
            }

            // --- Title ---
            OutlinedTextField(
                value = title,
                label = { Text("Title") },
                onValueChange = {
                    title = it
                    recordData.title = it
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
            )

//            Divider(modifier = Modifier.padding(start = 32.dp, end = 32.dp, bottom = 24.dp))

            // --- Description ---
            SelectionContainer(Modifier.fillMaxSize()) {
                OutlinedTextField(
                    value = description,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    onValueChange = {
                        description = it
                        recordData.description = it
                    },
                    label = { Text(randomGreeting) },
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(state = rememberScrollState())
                )
            }
        }

        if (openBottomSheet) {
            ModalBottomSheet(
                containerColor = BottomSheetContainerColor,
                onDismissRequest = { openBottomSheet = false },
                sheetState = bottomSheetState,
            ) {
                when (bottomNavItem) {
//                    BottomNavItem.Analyze -> AnalyseTextBSContent(
//                        viewModel,
//                        description
//                    )
                    BottomNavItem.Translate -> TranslatedTextBSContent(
                        text = "Some translated text"
                    )
                    BottomNavItem.Settings -> BackgroundColorPickerBSContent(
                        onClick = { color ->
                            backgroundColor = color
                            recordData.backgroundColor = color.toHex()
                        }
                    )
                    else -> {}
                }
            }
        }

        if (labelsDialog) {
            val labels = listOf(
                Label("1", "Thoughts", ""),
                Label("2", "Feelings", ""),
                Label("3", "Ideas", ""),
            )
            LabelsDialog(
                labels = labels,
                onDismissRequest = {},
                onLabelSelected = { label ->

                }
            )
        }

//        if (labelsDialog) {
//            AlertDialog(labelsDialog) {
//                Surface(
//                    modifier = Modifier
//                        .wrapContentWidth()
//                        .wrapContentHeight(),
//                    shape = MaterialTheme.shapes.large,
//                    tonalElevation = AlertDialogDefaults.TonalElevation
//                ) {
//                    Column(modifier = Modifier.padding(16.dp)) {
//                        Text(
//                            text = "This area typically contains the supportive text " +
//                                    "which presents the details regarding the Dialog's purpose.",
//                        )
//                        Spacer(modifier = Modifier.height(24.dp))
//                        TextButton(
//                            onClick = {
//                                openDialog = false
//                            },
//                            modifier = Modifier.align(Alignment.End)
//                        ) {
//                            Text("Confirm")
//                        }
//                    }
//                }
//            }
//        }
    }
}

@Composable
fun BottomSheetLayout() {
//    val coroutineScope = rememberCoroutineScope()
//    val modalSheetState = rememberModalBottomSheetState(
//        initialValue = ModalBottomSheetValue.Hidden,
//        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
//        skipHalfExpanded = false
//    )

    val value by rememberInfiniteTransition().animateFloat(
        initialValue = 1.dp.value,
        targetValue = 16.dp.value,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    Card(
        modifier = Modifier.padding(16.dp),
//        elevation = value.dp,
        elevation = CardDefaults.cardElevation(
            defaultElevation = value.dp
        ),
        shape = RoundedCornerShape(14.dp)
    ) {
        Text("Animation test")
    }
}

@Composable
fun DatePicker(context: Context) {
    val year: Int
    val month: Int
    val day: Int

    // Initializing a Calendar
    val calendar = Calendar.getInstance()

    // Fetching current year, month and day
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)

    calendar.time = Date()

    // Declaring a string value to
    // store date in string format
    val date = remember { mutableStateOf("") }

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, mMonth: Int, mDayOfMonth: Int ->
            date.value = "$mDayOfMonth/${mMonth + 1}/$year"
        }, year, month, day
    )
}

/*private fun getWords(text: String): List<String> {
    val regex = Regex("\\*(.*?)[\\*]")
    val matches = regex.findAll(text)
    return matches.map { it.groupValues[1] }.toList()
}

private fun formatString(text: String, boldWords: List<String>) = buildAnnotatedString {
    append(text)

    Timber.tag("highlightRussianWords").d("text: $text, boldWords: $boldWords")

    boldWords.forEach { word ->
        if (text.contains(word)) {
            val offsetStart = text.indexOf(word)
            val offsetEnd = offsetStart + word.length

            Timber.tag("highlightRussianWords").d("addStyle")

            addStyle(
                style = SpanStyle(fontWeight = FontWeight.Bold),
                start = offsetStart,
                end = offsetEnd
            )
        }
    }
}*/

//private fun getAnnotatedString(
//    description: String,
//    russianWordList: List<String>
//): AnnotatedString = buildAnnotatedString {
//    append(description)
//
////    Timber.tag("highlightRussianWords").d("getAnnotatedString: $description")
//
//    russianWordList.forEach { word ->
//        val startIndex = description.indexOf(word)
//        val endIndex = startIndex + word.length
//
//        addStyle(
//            style = SpanStyle(
//                background = Color(0xfffcfcb1),
//                color = Color(0xff64B5F6),
//                fontSize = 16.sp
//            ),
//            start = startIndex,
//            end = endIndex
//        )
//    }
//}

//@Preview
//@Composable
//fun Sandbox() {
//
//    Column(
//        modifier = Modifier
//            .background(Color.White)
//    ) {
//        Row(
//            Modifier
//                .weight(1F)
//                .fillMaxWidth()
//                .fillMaxHeight()
//        ) {
//            Text("desc 1")
//        }
//        Row(
//            Modifier
//                .weight(1F)
//                .fillMaxWidth()
//                .fillMaxHeight()
//        ) {
//            Text("desc 2")
//        }
//    }
//}