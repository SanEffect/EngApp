package com.san.englishbender.android.ui.flashcards

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mohamedrejeb.richeditor.annotation.ExperimentalRichTextApi
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.BasicRichTextEditor
import com.san.englishbender.android.core.extensions.toColor
import com.san.englishbender.android.core.extensions.toHex
import com.san.englishbender.android.ui.common.BackgroundColorPicker
import com.san.englishbender.android.ui.common.BaseDialogContent
import com.san.englishbender.android.ui.common.EBOutlinedButton
import com.san.englishbender.android.ui.common.EBOutlinedTextField
import com.san.englishbender.android.ui.common.richText.RichTextStyleRow
import com.san.englishbender.android.ui.common.widgets.ErrorView
import com.san.englishbender.android.ui.common.widgets.LoadingView
import com.san.englishbender.android.ui.theme.backgroundColors
import com.san.englishbender.core.extensions.ifNotEmpty
import com.san.englishbender.core.extensions.isNotNull
import com.san.englishbender.domain.entities.FlashCardEntity
import com.san.englishbender.ui.flashcards.BoardUiState
import com.san.englishbender.ui.flashcards.BoardsViewModel
import com.san.englishbender.ui.flashcards.FlashCardsUiState
import com.san.englishbender.ui.flashcards.FlashCardsViewModel
import com.wajahatkarim.flippable.FlipAnimationType
import com.wajahatkarim.flippable.Flippable
import com.wajahatkarim.flippable.rememberFlipController
import io.github.aakira.napier.log
import org.koin.androidx.compose.getViewModel


@Composable
fun BoardScreen(
    boardId: String?,
    onBackClick: () -> Unit
) {
    val viewModel: BoardsViewModel = getViewModel()
    val uiState by viewModel.boardUiState.collectAsStateWithLifecycle()

    LaunchedEffect(boardId) {
        boardId?.let { viewModel.getBoard(it) }
    }

    when {
        uiState.isLoading -> LoadingView()
        uiState.userMessage.isNotNull -> ErrorView(userMessage = uiState.userMessage)
        else -> BoardContent(
            viewModel,
            uiState,
            onBackClick
        )
    }
}

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class, ExperimentalRichTextApi::class
)
@Composable
fun BoardContent(
    viewModel: BoardsViewModel,
    uiState: BoardUiState,
    onBackClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val controller = rememberFlipController()
    val cards = uiState.board?.flashCards ?: emptyList()
    val pagerState = rememberPagerState(pageCount = { cards.size })
//    val pagerState = rememberPagerState(pageCount = { 1 })
    var boardCreationDialog by remember { mutableStateOf(false) }

    val containerColor = uiState.board?.backgroundColor?.toColor
        ?: MaterialTheme.colorScheme.surfaceVariant

    val richTextState = rememberRichTextState()
    richTextState.setConfig(
        linkColor = Color.Blue,
        linkTextDecoration = TextDecoration.Underline,
        codeColor = Color.DarkGray,
        codeBackgroundColor = Color.Transparent,
        codeStrokeColor = Color.Transparent,
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = containerColor,
        topBar = {
            key(containerColor) {
                TopAppBar(
                    modifier = Modifier.fillMaxWidth(),
                    title = {},
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = containerColor
                    ),
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
                        Icon(
                            rememberVectorPainter(Icons.Filled.Add),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier
                                .padding(8.dp)
                                .clickable { boardCreationDialog = true }
                        )
                        Icon(
                            rememberVectorPainter(Icons.Filled.Edit),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier
                                .padding(8.dp)
                                .clickable { }
                        )
                        Icon(
                            rememberVectorPainter(Icons.Filled.Delete),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier
                                .padding(8.dp)
                                .clickable { }
                        )
                    }
                )
            }
        }
    ) { paddingValues ->

        Column(Modifier.fillMaxSize()) {

            if (cards.isEmpty()) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Board is empty")
                }
                return@Scaffold
            }

            HorizontalPager(
                state = pagerState
            ) { pageIndex ->

                val card = cards[pageIndex]

                LaunchedEffect(Unit) {
                    card.back.ifNotEmpty { richTextState.setHtml(it) }
                }

//                val card = FlashCardEntity(
//                    front = "squander",
//                    back = "waste (something, especially money or time) in a reckless and foolish manner",
//                )

                Spacer(Modifier.height(32.dp))

                Flippable(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(600.dp)
                        .padding(paddingValues),
                    frontSide = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                                .border(1.dp, Color.Gray, RoundedCornerShape(6.dp))
                                .background(Color.White, RoundedCornerShape(6.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = card.front,
                                color = Color.Black,
                                fontSize = 20.sp
                            )
                        }
                    },
                    backSide = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                                .border(1.dp, Color.Gray, RoundedCornerShape(6.dp))
                                .background(Color.White, RoundedCornerShape(6.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            BasicRichTextEditor(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                                    .verticalScroll(state = rememberScrollState()),
                                state = richTextState,
                                textStyle = TextStyle(
                                    fontSize = 20.sp
                                ),
                                readOnly = true
                            )
                        }
                    },
                    flipController = controller,
                    flipAnimationType = FlipAnimationType.HORIZONTAL_CLOCKWISE
                )
            }
        }
    }

    if (boardCreationDialog) {
        CardCreationDialog(
            onCardCreate = { flashCard ->
                uiState.board?.let {
                    it.flashCards = it.flashCards.plus(listOf(flashCard))
                    viewModel.saveBoard(it)
                }
            },
            dismiss = { boardCreationDialog = false }
        )
    }
}

@OptIn(ExperimentalRichTextApi::class)
@Composable
fun CardCreationDialog(
    onCardCreate: (FlashCardEntity) -> Unit,
    dismiss: () -> Unit
) {
    BaseDialogContent(
        height = 450.dp,
        dismiss = dismiss
    ) {
        var word by remember { mutableStateOf("") }
        val card by remember { mutableStateOf(FlashCardEntity()) }

        val richTextState = rememberRichTextState()
        richTextState.setConfig(
            linkColor = Color.Blue,
            linkTextDecoration = TextDecoration.Underline,
            codeColor = Color.DarkGray,
            codeBackgroundColor = Color.Transparent,
            codeStrokeColor = Color.Transparent,
        )

        Column(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EBOutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = word,
                placeholder = "Word",
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                onValueChange = {
                    word = it
                    card.front = it
                }
            )

            Spacer(Modifier.height(16.dp))

            RichTextStyleRow(
                modifier = Modifier.padding(horizontal = 16.dp),
                state = richTextState
            )
            BasicRichTextEditor(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .border(1.dp, Color.Gray, RoundedCornerShape(6.dp))
                    .verticalScroll(state = rememberScrollState()),
                state = richTextState,
                minLines = 3,
                decorationBox = { innerTextField ->
                    Box(Modifier.padding(8.dp)) {
                        if (richTextState.annotatedString.text.isEmpty()) {
                            Text(
                                text = "Description",
                                color = Color.LightGray
                            )
                        }
                        innerTextField()
                    }
                }
            )

            Spacer(Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                EBOutlinedButton(
                    text = "Save",
                    onClick = {
                        if (card.front.isEmpty()) return@EBOutlinedButton

                        // TODO: delete it when RichTextEditor has onValueChanged callback
                        card.back = richTextState.toHtml()

                        onCardCreate(card)
                        dismiss()
                    }
                )
            }
        }
    }
}