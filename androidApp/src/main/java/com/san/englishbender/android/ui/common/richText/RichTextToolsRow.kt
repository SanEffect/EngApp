package com.san.englishbender.android.ui.common.richText

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.FormatAlignLeft
import androidx.compose.material.icons.automirrored.outlined.FormatAlignRight
import androidx.compose.material.icons.automirrored.outlined.FormatListBulleted
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohamedrejeb.richeditor.model.RichTextState

enum class RichTextTools {
    FormatAlignLeft,
    FormatAlignCenter,
    FormatAlignRight,
    Bold,
    Italic,
    Underline,
    FormatStrikethrough,
    FormatSize,
    FontColor,
    BackgroundColor,
    FormatListBulleted,
    FormatListNumbered,
    Code,
    Divider
}

val fullRichTextToolsPanel = RichTextTools.values().toList()
val shortRichTextToolsPanel = listOf(
    RichTextTools.Bold,
    RichTextTools.Italic,
    RichTextTools.Underline,
    RichTextTools.Divider,
    RichTextTools.FormatListBulleted,
    RichTextTools.FormatListNumbered,
)

@Composable
fun RichTextToolsRow(
    modifier: Modifier = Modifier,
    state: RichTextState,
    richTextTools: List<RichTextTools> = fullRichTextToolsPanel
) {
//    val currentParagraphStyle = state.currentParagraphStyle

    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        items(richTextTools) { item ->
            when (item) {
                RichTextTools.FormatAlignLeft -> RichTextStyleButton(
                    onClick = {
                        state.toggleParagraphStyle(ParagraphStyle(textAlign = TextAlign.Left))
                    },
                    isSelected = state.currentParagraphStyle.textAlign == TextAlign.Left,
                    icon = Icons.AutoMirrored.Outlined.FormatAlignLeft
                )

                RichTextTools.FormatAlignCenter -> RichTextStyleButton(
                    onClick = {
                        state.toggleParagraphStyle(ParagraphStyle(textAlign = TextAlign.Center))
                    },
                    isSelected = state.currentParagraphStyle.textAlign == TextAlign.Center,
                    icon = Icons.Outlined.FormatAlignCenter
                )

                RichTextTools.FormatAlignRight -> RichTextStyleButton(
                    onClick = {
                        state.toggleParagraphStyle(ParagraphStyle(textAlign = TextAlign.Right))
                    },
                    isSelected = state.currentParagraphStyle.textAlign == TextAlign.Right,
                    icon = Icons.AutoMirrored.Outlined.FormatAlignRight
                )

                RichTextTools.Bold -> RichTextStyleButton(
                    onClick = {
                        state.toggleSpanStyle(SpanStyle(fontWeight = FontWeight.Bold))
                    },
                    isSelected = state.currentSpanStyle.fontWeight == FontWeight.Bold,
                    icon = Icons.Outlined.FormatBold
                )

                RichTextTools.Italic -> RichTextStyleButton(
                    onClick = {
                        state.toggleSpanStyle(SpanStyle(fontStyle = FontStyle.Italic))
                    },
                    isSelected = state.currentSpanStyle.fontStyle == FontStyle.Italic,
                    icon = Icons.Outlined.FormatItalic
                )

                RichTextTools.Underline -> RichTextStyleButton(
                    onClick = {
                        state.toggleSpanStyle(SpanStyle(textDecoration = TextDecoration.Underline))
                    },
                    isSelected = state.currentSpanStyle.textDecoration?.contains(TextDecoration.Underline) == true,
                    icon = Icons.Outlined.FormatUnderlined
                )

                RichTextTools.FormatStrikethrough -> RichTextStyleButton(
                    onClick = {
                        state.toggleSpanStyle(SpanStyle(textDecoration = TextDecoration.LineThrough))
                    },
                    isSelected = state.currentSpanStyle.textDecoration?.contains(TextDecoration.LineThrough) == true,
                    icon = Icons.Outlined.FormatStrikethrough
                )

                RichTextTools.FormatSize -> RichTextStyleButton(
                    onClick = {
                        state.toggleSpanStyle(SpanStyle(fontSize = 28.sp))
                    },
                    isSelected = state.currentSpanStyle.fontSize == 28.sp,
                    icon = Icons.Outlined.FormatSize
                )

                RichTextTools.FontColor -> RichTextStyleButton(
                    onClick = {
                        state.toggleSpanStyle(SpanStyle(color = Color.Red))
                    },
                    isSelected = state.currentSpanStyle.color == Color.Red,
                    icon = Icons.Filled.Circle,
                    tint = Color.Red
                )

                RichTextTools.BackgroundColor -> RichTextStyleButton(
                    onClick = {
                        state.toggleSpanStyle(SpanStyle(background = Color.Yellow))
                    },
                    isSelected = state.currentSpanStyle.background == Color.Yellow,
                    icon = Icons.Outlined.Circle,
                    tint = Color.Yellow
                )

                RichTextTools.FormatListBulleted -> RichTextStyleButton(
                    onClick = { state.toggleUnorderedList() },
                    isSelected = state.isUnorderedList,
                    icon = Icons.AutoMirrored.Outlined.FormatListBulleted,
                )

                RichTextTools.FormatListNumbered -> RichTextStyleButton(
                    onClick = { state.toggleOrderedList() },
                    isSelected = state.isOrderedList,
                    icon = Icons.Outlined.FormatListNumbered,
                )

                RichTextTools.Code -> RichTextStyleButton(
                    onClick = { state.toggleCodeSpan() },
                    isSelected = state.isCodeSpan,
                    icon = Icons.Outlined.Code,
                )

                RichTextTools.Divider -> VerticalDivider(
                    Modifier
                        .height(24.dp)
                        .background(Color(0xFF393B3D))
                )
            }
        }

//        item {
//            VerticalDivider(
//                Modifier
//                    .height(24.dp)
//                    .background(Color(0xFF393B3D))
//            )
//            Box(
//                Modifier
//                    .height(24.dp)
//                    .width(1.dp)
//                    .background(Color(0xFF393B3D))
//            )
    }
}