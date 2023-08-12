package com.san.englishbender.android.ui.labels

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.san.englishbender.android.core.extensions.toColor
import com.san.englishbender.android.ui.common.BaseDialogContent
import com.san.englishbender.android.ui.common.EBOutlinedButton
import com.san.englishbender.android.ui.theme.selectedLabelColor
import com.san.englishbender.ui.LabelsViewModel
import database.Label
import io.github.aakira.napier.log


@Composable
fun ListLabelScreen(
    labelsViewModel: LabelsViewModel,
    createLabel: (String?) -> Unit = {},
    dismiss: () -> Unit = {}
) {
    log(tag = "navEntriesCheck") { "ListLabelScreen" }
    val uiState by labelsViewModel.uiState.collectAsStateWithLifecycle()
//    val labels by labelsViewModel.labels.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        labelsViewModel.getLabels()
    }

    log { "labels: ${uiState.labels}" }

    val selectedLabels = remember { mutableStateListOf("") }

    BaseDialogContent(
        height = 350.dp,
        dismiss = dismiss
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp),
                text = "Labels",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Column(modifier = Modifier.weight(1f)) {
                LazyColumn {
                    items(uiState.labels.size) { index ->
                        val label = uiState.labels[index]
                        val hasLabel = selectedLabels.indexOf(label.id) != -1
                        val borderColor = if (hasLabel) selectedLabelColor else Color.DarkGray

                        LabelRow(
                            label = label,
                            borderColor = borderColor,
                            onClick = {
                                if (hasLabel) selectedLabels.remove(label.id)
                                else selectedLabels.add(label.id)
                            },
                            onEditClick = { createLabel(label.id) }
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                EBOutlinedButton(
                    text = "Create",
                    onClick = { createLabel(null) }
                )

                EBOutlinedButton(
                    text = "Close",
                    onClick = dismiss
                )
            }
        }
    }
}

@Composable
fun LabelRow(
    label: Label,
    borderColor: Color = Color.Gray,
    onClick: (String) -> Unit,
    onEditClick: () -> Unit,
) {
    Row(
        Modifier
            .width(300.dp)
            .padding(2.dp)
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(7f)
                .padding(2.dp)
                .background(label.color.toColor, shape = RoundedCornerShape(4.dp))
                .border(1.dp, borderColor, RoundedCornerShape(4.dp))
                .clickable { onClick(label.id) }
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = label.name,
                fontSize = 12.sp
            )
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(2.dp)
                .background(Color.White)
                .border(1.dp, Color.DarkGray, RoundedCornerShape(4.dp))
                .clickable { onEditClick() },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                Icons.Outlined.Edit,
                contentDescription = null,
                modifier = Modifier
                    .padding(6.dp)
                    .size(18.dp)
            )
        }

    }
}

@Preview
@Composable
fun LabelRowPreview(

) {
    Row(
        Modifier
            .width(300.dp)
            .padding(2.dp)
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(7f)
                .padding(2.dp)
                .background(Color.Green, shape = RoundedCornerShape(4.dp))
                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                .clickable { }
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Label",
                fontSize = 12.sp
            )
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(2.dp)
                .background(Color.White)
                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                Icons.Outlined.Edit,
                contentDescription = null,
                modifier = Modifier
                    .padding(6.dp)
                    .size(18.dp)
                    .clickable { }
            )
        }

    }
}