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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mohamedrejeb.richeditor.annotation.ExperimentalRichTextApi
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.BasicRichTextEditor
import com.san.englishbender.android.core.extensions.toColor
import com.san.englishbender.android.ui.common.BaseDialogContent
import com.san.englishbender.android.ui.common.EBOutlinedButton
import com.san.englishbender.android.ui.common.EBOutlinedTextField
import com.san.englishbender.android.ui.common.EBTextButton
import com.san.englishbender.android.ui.common.richText.RichTextStyleRow
import com.san.englishbender.android.ui.common.widgets.ErrorView
import com.san.englishbender.android.ui.common.widgets.LoadingView
import com.san.englishbender.android.ui.theme.RedDark
import com.san.englishbender.core.extensions.ifNotEmpty
import com.san.englishbender.core.extensions.isNotNull
import com.san.englishbender.core.extensions.isNull
import com.san.englishbender.domain.entities.BoardEntity
import com.san.englishbender.domain.entities.FlashCardEntity
import com.san.englishbender.ui.flashcards.BoardUiState
import com.san.englishbender.ui.flashcards.BoardsViewModel
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

//    log(tag = "ExceptionHandling") { "BoardScreen.uiState.board: ${uiState.board}" }

    LaunchedEffect(boardId) {
        boardId?.let {
            log(tag = "FuckThisShit") { "viewModel.getBoard(it)" }
            viewModel.getBoard(it) }
    }

//    LaunchedEffect(viewModel.boardIsSaved.value) {
//        log(tag = "FuckThisShit") { "viewModel.boardIsSaved" }
//        viewModel.boardIsSaved.value.getContentIfNotHandled()?.let {
//            boardId?.let {
//                log(tag = "FuckThisShit") { "viewModel.getBoard(it)" }
//                viewModel.getBoard(it) }
//        }
//    }

    when {
        uiState.isLoading -> LoadingView()
        uiState.userMessage.isNotNull -> ErrorView(userMessage = uiState.userMessage)
        else -> BoardContent(
            uiState,
            onCardCreate = { board, flashCard -> viewModel.addCardToBoard(board, flashCard) },
            onCardUpdate = { flashCard -> viewModel.updateCard(flashCard) },
            onCardDelete = { flashCardId -> viewModel.deleteCard(flashCardId) },
            onBackClick
        )
    }
}

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class,
    ExperimentalRichTextApi::class
)
@Composable
fun BoardContent(
    uiState: BoardUiState,
    onCardCreate: (BoardEntity, FlashCardEntity) -> Unit,
    onCardUpdate: (FlashCardEntity) -> Unit,
    onCardDelete: (String) -> Unit,
    onBackClick: () -> Unit = {},
) {
    val controller = rememberFlipController()
    val focusManager = LocalFocusManager.current
    val cards = uiState.board?.flashCards ?: emptyList()

    log(tag = "FuckThisShit") { "cards.size: ${cards.size}" }

    cards.find { it.id == "0786d6a4-f235-4016-a58b-5040324af033" }?.let {
        log(tag = "ExceptionHandling") { "front: ${it.frontText}" }
        log(tag = "ExceptionHandling") { "back: ${it.backText}" }
    }

    val pagerState = rememberPagerState(pageCount = { cards.size })
    var addCardDialog by remember { mutableStateOf(false) }
    var editCardDialog by remember { mutableStateOf(false) }
    var cardDeletionDialog by remember { mutableStateOf(false) }

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
                                .clickable { addCardDialog = true }
                        )
                        Icon(
                            rememberVectorPainter(Icons.Filled.Edit),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier
                                .padding(8.dp)
                                .clickable { editCardDialog = true }
                        )
                        Icon(
                            rememberVectorPainter(Icons.Filled.Delete),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier
                                .padding(8.dp)
                                .clickable { cardDeletionDialog = true }
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

            Spacer(Modifier.height(32.dp))

            HorizontalPager(
                state = pagerState
            ) { pageIndex ->
                val card = cards.getOrNull(pageIndex) ?: return@HorizontalPager

//                log(tag = "ExceptionHandling") { "Pager card: $card" }

                LaunchedEffect(card) {
                    card.backText.ifNotEmpty { richTextState.setHtml(it) }
                }

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
                                text = card.frontText,
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
                                textStyle = TextStyle(fontSize = 20.sp),
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

    when {
        addCardDialog -> AddCardDialog(
            onCreate = { flashCard ->
                focusManager.clearFocus()
                uiState.board?.let { onCardCreate(it, flashCard) }
            },
            dismiss = { addCardDialog = false })
        // ---
        editCardDialog -> EditCardDialog(
            currentFlashCard = cards.getOrNull(pagerState.currentPage),
            onUpdate = { flashCard ->
                focusManager.clearFocus()
                onCardUpdate(flashCard)
            },
            dismiss = { editCardDialog = false })
        // ---
        cardDeletionDialog -> {
            val card = cards.getOrNull(pagerState.currentPage) ?: return

            CardDeletionDialog(
                flashCard = card,
                confirm = { cardId -> onCardDelete(cardId) },
                dismiss = { cardDeletionDialog = false }
            )
        }
    }
}

@Composable
fun AddCardDialog(
    onCreate: (FlashCardEntity) -> Unit,
    dismiss: () -> Unit
) {
    CardContent(
        onSave = onCreate,
        dismiss = dismiss
    )
}

@Composable
fun EditCardDialog(
    currentFlashCard: FlashCardEntity?,
    onUpdate: (FlashCardEntity) -> Unit,
    dismiss: () -> Unit
) {
    CardContent(
        currentFlashCard,
        onSave = onUpdate,
        dismiss = dismiss
    )
}

@OptIn(ExperimentalRichTextApi::class)
@Composable
fun CardContent(
    flashCard: FlashCardEntity? = null,
    richTextState: RichTextState = rememberRichTextState(),
    onSave: (FlashCardEntity) -> Unit,
    dismiss: () -> Unit
) {
    BaseDialogContent(
        height = 450.dp,
        dismiss = dismiss
    ) {
        var word by remember { mutableStateOf("") }
        val card by remember { mutableStateOf(flashCard ?: FlashCardEntity()) }

        flashCard?.let {
            LaunchedEffect(it) {
                word = it.frontText
                richTextState.setHtml(it.backText)
            }
        }

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
                    card.frontText = it
                }
            )

            Spacer(Modifier.height(16.dp))

            RichTextStyleRow(
                modifier = Modifier.padding(horizontal = 8.dp),
                state = richTextState
            )
            BasicRichTextEditor(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .border(1.dp, Color.Gray, RoundedCornerShape(6.dp))
                    .verticalScroll(state = rememberScrollState()),
                state = richTextState,
                minLines = 12,
                maxLines = 12,
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
                        if (word.isEmpty()) return@EBOutlinedButton

                        // TODO: delete it when RichTextEditor has onValueChanged callback
                        card.backText = richTextState.toHtml()

                        onSave(card)
//                        dismiss()
                    }
                )
            }
        }
    }
}

@Composable
fun CardDeletionDialog(
    flashCard: FlashCardEntity,
    confirm: (String) -> Unit,
    dismiss: () -> Unit
) {
    Dialog(onDismissRequest = dismiss) {
        Column {
            Spacer(Modifier.height(8.dp))

            Text(
                text = "Warning",
                fontSize = 20.sp
            )

            Spacer(Modifier.height(16.dp))

            Text(
                modifier = Modifier.padding(top = 16.dp, bottom = 24.dp),
                text = "Are you sure to delete the card \"${flashCard.frontText}\"?",
                fontSize = 16.sp
            )

            Spacer(Modifier.height(24.dp))

            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(Modifier.weight(1f))
                EBTextButton(
                    text = "Cancel",
                    onClick = dismiss
                )
                EBTextButton(
                    modifier = Modifier.padding(start = 16.dp),
                    text = "Delete",
                    colors = ButtonDefaults.buttonColors(contentColor = RedDark),
                    onClick = { confirm(flashCard.id) }
                )
            }
        }
    }
}

//@Preview
//@Composable
//fun BoardContentPreview() {
//    BoardContent(
//        uiState = BoardUiState(),
//        onCardCreate = { board, card ->  },
//        onCardUpdate = {},
//        onCardDelete = {}
//    )
//}