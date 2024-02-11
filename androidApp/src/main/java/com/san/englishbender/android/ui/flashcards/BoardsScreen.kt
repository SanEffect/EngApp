package com.san.englishbender.android.ui.flashcards

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.san.englishbender.android.core.extensions.toHex
import com.san.englishbender.android.ui.common.BackgroundColorPicker
import com.san.englishbender.android.ui.common.BaseDialogContent
import com.san.englishbender.android.ui.common.EBOutlinedButton
import com.san.englishbender.android.ui.common.EBOutlinedTextField
import com.san.englishbender.android.ui.common.widgets.ErrorView
import com.san.englishbender.android.ui.common.widgets.LoadingView
import com.san.englishbender.android.ui.theme.backgroundColors
import com.san.englishbender.core.extensions.isNotNull
import com.san.englishbender.domain.entities.BoardEntity
import com.san.englishbender.ui.flashcards.BoardsUiState
import com.san.englishbender.ui.flashcards.BoardsViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun BoardsScreen(
    onBoardClick: (String?) -> Unit,
    openDrawer: () -> Unit
) {
    val viewModel: BoardsViewModel = getViewModel()
    val uiState by viewModel.boardsUiState.collectAsStateWithLifecycle()

    when {
        uiState.isLoading -> LoadingView()
        uiState.userMessage.isNotNull -> ErrorView(userMessage = uiState.userMessage)
        else -> BoardsContent(
            viewModel,
            uiState,
            onBoardClick = onBoardClick,
            openDrawer = openDrawer
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoardsContent(
    viewModel: BoardsViewModel,
    uiState: BoardsUiState,
    onBoardClick: (String?) -> Unit,
    openDrawer: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    var boardCreationDialog by remember { mutableStateOf(false) }

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
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                contentColor = Color.White,
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                shape = RoundedCornerShape(10.dp),
                onClick = { boardCreationDialog = true }
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
            items(items = uiState.boards, key = { it.id }) { board ->
                BoardItem(board, onBoardClick)
            }
        }
    }
    if (boardCreationDialog) {
        BoardCreationDialog(
            viewModel,
            dismiss = {
                focusManager.clearFocus()
                boardCreationDialog = false
            }
        )
    }
}

@Composable
fun BoardCreationDialog(
    viewModel: BoardsViewModel,
    dismiss: () -> Unit
) {
    BaseDialogContent(
        height = 250.dp,
        dismiss = dismiss
    ) {
        var board by remember { mutableStateOf(BoardEntity()) }
        var boardName by remember { mutableStateOf("") }
        var boardColor by remember { mutableStateOf(backgroundColors.first()) }

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
                value = board.name,
                placeholder = "Board name",
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                onValueChange = { board.name = it }
            )

            BackgroundColorPicker(
                modifier = Modifier.padding(
                    vertical = 12.dp,
                    horizontal = 8.dp
                ),
                label = "",
                listState = rememberLazyListState(),
                onClick = { color: Color -> board.backgroundColor = color.toHex() }
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
                        if (board.name.isEmpty()) return@EBOutlinedButton

                        viewModel.saveBoard(board)
                        dismiss()
                    }
                )
            }
        }
    }
}

@Composable
fun BoardItem(
    board: BoardEntity,
    onBoardClick: (String?) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
            .border(1.dp, Color.Gray, RoundedCornerShape(6.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .padding(12.dp)
                .clickable { onBoardClick(board.id) },
            text = board.name
        )
    }
}

//@Composable
//fun rememberImeState(): State<Boolean> {
//    val imeState = remember {
//        mutableStateOf(false)
//    }
//
//    val view = LocalView.current
//    DisposableEffect(view) {
//        val listener = ViewTreeObserver.OnGlobalLayoutListener {
//            val isKeyboardOpen = ViewCompat.getRootWindowInsets(view)
//                ?.isVisible(WindowInsetsCompat.Type.ime()) ?: true
//            imeState.value = isKeyboardOpen
//        }
//
//        view.viewTreeObserver.addOnGlobalLayoutListener(listener)
//        onDispose {
//            view.viewTreeObserver.removeOnGlobalLayoutListener(listener)
//        }
//    }
//    return imeState
//}