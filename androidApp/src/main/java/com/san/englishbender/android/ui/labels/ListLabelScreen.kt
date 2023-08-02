package com.san.englishbender.android.ui.labels

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.san.englishbender.android.core.extensions.toColor
import com.san.englishbender.android.ui.common.BaseDialogContent
import com.san.englishbender.android.ui.theme.selectedLabelColor
import com.san.englishbender.ui.LabelsViewModel
import io.github.aakira.napier.log


@Composable
fun ListLabelScreen(
    labelsViewModel: LabelsViewModel,
    createLabel: () -> Unit = {},
    dismiss: () -> Unit = {}
) {
    log { "AddLabelScreen" }
    val uiState by labelsViewModel.uiState.collectAsStateWithLifecycle()

    log { "labels: ${uiState.labels}" }

//    val selectedLabels by remember { mutableStateOf(mutableListOf<String>()) }
    val selectedLabels = remember { mutableStateListOf("") }

    BaseDialogContent(dismiss = dismiss) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .height(300.dp)
    ) {

        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp),
            text = "Labels",
            fontSize = 16.sp
        )

        Column {
            LazyColumn(
                modifier = Modifier.heightIn(0.dp, 200.dp)
            ) {
                items(uiState.labels.size) { index ->
                    val label = uiState.labels[index]
                    val labelName = label.name
                    val color = label.color.toColor

                    val hasLabel = selectedLabels.indexOf(label.id) != -1
                    val borderColor =
                        if (hasLabel) selectedLabelColor else Color.LightGray

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(2.dp)
                            .background(color, shape = RoundedCornerShape(4.dp))
                            .border(1.dp, borderColor, RoundedCornerShape(4.dp))
                            .clickable {
//                                                onLabelSelected(listOf(label))

                                if (hasLabel) selectedLabels.remove(label.id)
                                else selectedLabels.add(label.id)
                            }
                    ) {
                        Text(
                            modifier = Modifier.padding(
                                start = 8.dp,
                                top = 8.dp,
                                bottom = 8.dp
                            ),
                            text = labelName,
                            fontSize = 12.sp
                        )
                        Icon(
                            Icons.Outlined.Edit,
                            contentDescription = null,
                            modifier = Modifier
                                .size(24.dp)
                                .padding(end = 8.dp)
                                .clickable { }
                        )
                    }
                }
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    createLabel()
                },
                shape = RoundedCornerShape(4.dp),
                border = BorderStroke(1.dp, Color.Gray),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.DarkGray)
            ) {
                Text(
                    text = "Create a new label",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
            }
        }

        Row(
            modifier = Modifier.weight(0.3f),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom
        ) {
            TextButton(
                onClick = { dismiss() }
            ) {
                Text(
                    text = "Close",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
    }
}