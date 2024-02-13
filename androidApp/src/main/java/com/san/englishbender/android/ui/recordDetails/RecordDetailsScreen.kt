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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mohamedrejeb.richeditor.annotation.ExperimentalRichTextApi
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.BasicRichTextEditor
import com.san.englishbender.Strings
import com.san.englishbender.android.core.extensions.toColor
import com.san.englishbender.android.core.extensions.toHex
import com.san.englishbender.android.ui.common.EBOutlinedButton
import com.san.englishbender.android.ui.common.richText.RichTextStyleRow
import com.san.englishbender.android.ui.recordDetails.bottomSheets.BackgroundColorPickerBSContent
import com.san.englishbender.android.ui.recordDetails.bottomSheets.GrammarCheckBSContent
import com.san.englishbender.android.ui.recordDetails.bottomSheets.TranslatedTextBSContent
import com.san.englishbender.android.ui.tags.TagsPager
import com.san.englishbender.android.ui.theme.BottomSheetContainerColor
import com.san.englishbender.android.ui.theme.RedDark
import com.san.englishbender.core.AppConstants
import com.san.englishbender.core.extensions.ifNotEmpty
import com.san.englishbender.ui.recordDetails.DetailUiState
import com.san.englishbender.ui.recordDetails.RecordDetailsViewModel
import org.koin.androidx.compose.getViewModel
import java.util.Calendar
import java.util.Date


@Composable
fun RecordDetailsScreen(
    onBackClick: () -> Unit,
    recordId: String?
) {
    val viewModel: RecordDetailsViewModel = getViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(recordId) {
        recordId?.let { viewModel.getRecord(it) }
    }

    RecordDetailsContent(
        uiState,
        viewModel,
        onBackClick,
    )

    DisposableEffect(LocalLifecycleOwner.current) {
        onDispose {
//            viewModel.saveDraft(uiState.record)
            viewModel.resetUiState()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalRichTextApi::class)
@Composable
fun RecordDetailsContent(
    uiState: DetailUiState,
    viewModel: RecordDetailsViewModel,
    onBackClick: () -> Unit
) {

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
//    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val bottomSheetState = rememberModalBottomSheetState()
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }

    val richTextState = rememberRichTextState()
    richTextState.setConfig(
        linkColor = Color.Blue,
        linkTextDecoration = TextDecoration.Underline,
        codeColor = Color.DarkGray,
        codeBackgroundColor = Color.Transparent,
        codeStrokeColor = Color.Transparent,
    )

    LaunchedEffect(uiState.userMessage) {
        uiState.userMessage.getContentIfNotHandled()?.let {
            snackbarHostState.showSnackbar(Strings(context).get(it))
        }
    }

    LaunchedEffect(uiState.record.text) {
        uiState.record.text.ifNotEmpty { richTextState.setHtml(it) }
    }

//    var originalText by rememberSaveable(viewState) { mutableStateOf(viewState.originalText) }
//    val translatedText by rememberSaveable(viewState) { mutableStateOf(viewState.translatedText) }
//    val showTranslatedText by rememberSaveable(viewState) { mutableStateOf(viewState.showTranslatedText) }

    val record by remember(uiState.record) { mutableStateOf(uiState.record) }

    var title by rememberSaveable(record) { mutableStateOf(record.title) }
//    uiState.record.text.ifNotEmpty { richTextState.setMarkdown(it) }
    val randomGreeting = remember(record) { AppConstants.GREETINGS.random() }

//    log(tag = "richTextState") { "text: ${uiState.record.text}" }
//    log(tag = "richTextState") { "plainText: ${uiState.record.plainText}" }

    var containerColor by remember {
        mutableStateOf(
            if (record.backgroundColor.isEmpty()) Color.White
            else record.backgroundColor.toColor
        )
    }
    var bottomNavItem by remember { mutableStateOf<BottomNavItem>(BottomNavItem.Translate) }
    var tagsDialog by remember { mutableStateOf(false) }
    val selectedTags = remember(record) {
        record.tags?.toMutableStateList() ?: mutableStateListOf()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = containerColor,
        topBar = {
            key(containerColor) {
                TopAppBar(
                    modifier = Modifier.fillMaxWidth(),
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = containerColor
                    ),
                    title = {
                        Text(
                            if (record.title.isEmpty()) "New Record" else "Record Details",
                            textAlign = TextAlign.Start,
                            modifier = Modifier.fillMaxWidth(),
//                        style = JetRortyTypography.h2
                        )
                    },
                    navigationIcon = {
                        Icon(
                            rememberVectorPainter(Icons.AutoMirrored.Filled.ArrowBack),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .clickable { onBackClick() }
                        )
                    },
                    actions = {
                        EBOutlinedButton(
                            modifier = Modifier.padding(end = 8.dp),
                            text = "Save",
                            onClick = {
                                focusManager.clearFocus()
                                // TODO: delete it when RichTextEditor has onValueChanged callback
                                record.text = richTextState.toHtml()
                                record.plainText = richTextState.annotatedString.text
                                viewModel.saveRecord(record, selectedTags)
                            }
                        )
                    }
                )
            }
        },
        bottomBar = {
            NavigationBar(
                containerColor = containerColor,
                navItemClicked = { navItem ->
                    bottomNavItem = navItem
                    openBottomSheet = true
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
        ) {
            if (record.isDraft) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 12.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(text = "Draft", color = RedDark)
                }
            }

            // --- Tags
            TagsRow(
                selectedTags = selectedTags,
                onDeleteTagClick = { tagId ->
                    selectedTags.removeIf { it.id == tagId }
                },
                onMoreTagsClick = { tagsDialog = true }
            )

            // --- Title
            OutlinedTextField(
                value = title,
                label = { Text("Title") },
                onValueChange = {
                    title = it
                    record.title = it
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

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                color = Color.LightGray
            )

            // --- Description
            RichTextStyleRow(
                modifier = Modifier.padding(horizontal = 8.dp),
                state = richTextState
            )

//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.Start
//            ) {
//                EBOutlinedIconButton(
//                    imageVector = Icons.Outlined.FormatAlignLeft,
//                    colors = ButtonDefaults.buttonColors(containerColor = if (isLeft) Color.LightGray else Color.White),
//                    onClick = {
//                        richTextState.addParagraphStyle(ParagraphStyle(textAlign = TextAlign.Left))
//                        log(tag = "RichTextStyleRow") {
//                            "textAlign: ${richTextState.currentParagraphStyle.textAlign}"
//                        }
//                    },
//                )
//                EBOutlinedIconButton(
//                    imageVector = Icons.Outlined.FormatAlignCenter,
//                    colors = ButtonDefaults.buttonColors(containerColor = if (isCenter) Color.LightGray else Color.White),
//                    onClick = {
//                        richTextState.addParagraphStyle(ParagraphStyle(textAlign = TextAlign.Center))
//                        log(tag = "RichTextStyleRow") {
//                            "textAlign: ${richTextState.currentParagraphStyle.textAlign}"
//                        }
//                    },
//                )
//            }

            BasicRichTextEditor(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .verticalScroll(state = rememberScrollState()),
                state = richTextState,
                decorationBox = { innerTextField ->
                    Box(Modifier.padding(8.dp)) {
                        if (richTextState.annotatedString.text.isEmpty()) {
                            Text(
                                text = randomGreeting,
                                color = Color.LightGray
                            )
                        }
                        innerTextField()
                    }
                }
            )
        }

        if (openBottomSheet) {
            ModalBottomSheet(
                containerColor = BottomSheetContainerColor,
                onDismissRequest = { openBottomSheet = false },
                sheetState = bottomSheetState,
            ) {
                focusManager.clearFocus()
                when (bottomNavItem) {
                    BottomNavItem.GrammarCheck -> GrammarCheckBSContent(
                        viewModel,
                        richTextState.annotatedString.text
                    )

                    BottomNavItem.Translate -> TranslatedTextBSContent(
                        text = "Some translated text"
                    )

                    BottomNavItem.Settings -> BackgroundColorPickerBSContent(
                        onClick = { color ->
                            containerColor = color
                            record.backgroundColor = color.toHex()
                        }
                    )
                }
            }
        }
    }
    if (tagsDialog) {
        TagsPager(
            recordTags = selectedTags,
            dismiss = { tagsDialog = false },
            onTagClick = { tags ->
                selectedTags.clear()
                selectedTags.addAll(tags)
            }
        )
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

    val value by rememberInfiniteTransition(label = "").animateFloat(
        initialValue = 1.dp.value,
        targetValue = 16.dp.value,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
            ),
            repeatMode = RepeatMode.Reverse
        ), label = ""
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