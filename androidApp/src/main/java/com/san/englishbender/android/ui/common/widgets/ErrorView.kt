package com.san.englishbender.android.ui.common.widgets

//import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.san.englishbender.SharedRes
import com.san.englishbender.android.R
import com.san.englishbender.android.core.extensions.stringResource
import com.san.englishbender.ui.common.SmallSpacer
import dev.icerock.moko.resources.StringResource

@Suppress("ForbiddenComment")
@Composable
fun ErrorView(
    modifier: Modifier = Modifier,
    userMessage: StringResource?,
    action: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .wrapContentHeight(Alignment.CenterVertically)
    ) {
        Icon(
            painter = rememberVectorPainter(Icons.Default.AccountBox),
            contentDescription = null,
            tint = Red,
            modifier = modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
        )
        SmallSpacer()
        Text(
            text = stringResource(userMessage ?: SharedRes.strings.something_went_wrong),
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            textAlign = TextAlign.Center
        )
        SmallSpacer()
        Button(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center),
            onClick = action
        ) {
            Text(text = stringResource(id = R.string.text_retry))
        }
    }
}

//@Preview(
//    showBackground = true,
//    name = "Light Mode"
//)
//@Preview(
//    showBackground = true,
//    uiMode = Configuration.UI_MODE_NIGHT_YES,
//    name = "Dark Mode"
//)
//@Composable
//fun ErrorPageViewPreview() {
//    EnglishBenderTheme {
//        ErrorView(e = Exception()) {}
//    }
//}