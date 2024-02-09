package com.san.englishbender.android.ui.flashcards

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.san.englishbender.android.ui.common.EBOutlinedButton
import com.san.englishbender.android.ui.common.widgets.ErrorView
import com.san.englishbender.android.ui.common.widgets.LoadingView
import com.san.englishbender.android.ui.theme.BottomSheetContainerColor
import com.san.englishbender.core.extensions.isNotNull
import com.san.englishbender.domain.entities.BoardEntity
import com.san.englishbender.ui.flashcards.FlashCardsUiState
import com.san.englishbender.ui.flashcards.FlashCardsViewModel
import org.koin.androidx.compose.getViewModel
import android.view.ViewTreeObserver
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.accompanist.insets.ProvideWindowInsets

@Composable
fun FlashCardsScreen(
    onBoardClick: (String?) -> Unit,
    openDrawer: () -> Unit
) {
    val viewModel: FlashCardsViewModel = getViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when {
        uiState.isLoading -> LoadingView()
        uiState.userMessage.isNotNull -> ErrorView(userMessage = uiState.userMessage)
        else -> FlashCardsContent(
            viewModel,
            uiState,
            onBoardClick = onBoardClick,
            openDrawer = openDrawer
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashCardsContent(
    viewModel: FlashCardsViewModel,
    uiState: FlashCardsUiState,
    onBoardClick: (String?) -> Unit,
    openDrawer: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val bottomSheetState = rememberModalBottomSheetState()
    var boardCreationBottomSheet by remember { mutableStateOf(false) }

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
                backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
                shape = RoundedCornerShape(10.dp),
                onClick = { boardCreationBottomSheet = true }
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
                BoardCard(board, viewModel, onBoardClick)
            }
        }

        if (boardCreationBottomSheet) {
            ModalBottomSheet(
                modifier = Modifier.imePadding(),
                containerColor = BottomSheetContainerColor,
                onDismissRequest = { boardCreationBottomSheet = false },
                sheetState = bottomSheetState
            ) {
                focusManager.clearFocus()
                BoardCreationBSContent(viewModel)
            }
        }
    }
}

@Composable
fun BoardCreationBSContent(viewModel: FlashCardsViewModel) {

    var boardName by remember { mutableStateOf("") }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = boardName,
            placeholder = { Text("Board name") },
            onValueChange = {
                boardName = it
            }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, end = 16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            EBOutlinedButton(
                text = "Save",
                onClick = {
                    viewModel.saveBoard(boardName)
                }
            )
        }
    }
}

@Composable
fun BoardCard(
    board: BoardEntity,
    flashCardsViewModel: FlashCardsViewModel,
    onBoardClick: (String?) -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(start = 12.dp)
            .border(1.dp, Color.Gray, RoundedCornerShape(6.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(board.name)
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